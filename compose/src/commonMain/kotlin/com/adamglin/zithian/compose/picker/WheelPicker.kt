package com.adamglin.zithian.compose.picker

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.adamglin.zithian.compose.theme.InteractType
import com.adamglin.zithian.compose.theme.LocalInteractType
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@Composable
fun rememberWheelPickerState(initialIndex: Int = 0): WheelPickerState {
    return rememberSaveable(saver = WheelPickerState.Saver) {
        WheelPickerState(initialIndex)
    }
}

@Stable
class WheelPickerState(
    private val initialIndex: Int = 0
) {
    internal var _lazyListState: LazyListState? = null
    val lazyListState: LazyListState
        get() = _lazyListState ?: error("WheelPickerState is not attached to a WheelPicker")

    // Use a reasonable multiplier to simulate infinite scrolling
    // This provides ~5000 full cycles in each direction which is more than enough
    internal companion object {
        const val VIRTUAL_MULTIPLIER = 10000

        val Saver = androidx.compose.runtime.saveable.Saver<WheelPickerState, Int>(
            save = {
                it.currentIndex
            },
            restore = { WheelPickerState(it) }
        )
    }

    // Calculated in attach to ensure alignment
    internal var initialScrollIndex = 0
    internal var virtualCount = 0
        private set
    private var itemCount = 0

    internal fun initialize(count: Int, isInfinite: Boolean) {
        itemCount = count
        if (count > 0) {
            if (isInfinite) {
                virtualCount = count * VIRTUAL_MULTIPLIER
                val infiniteCenter = virtualCount / 2
                val offset = infiniteCenter % count
                initialScrollIndex = infiniteCenter - offset + initialIndex
            } else {
                virtualCount = count
                initialScrollIndex = initialIndex.coerceIn(0, count - 1)
            }
        }
    }

    // Holds the initial index until we can apply it to the list
    private var pendingInitialIndex: Int? = initialIndex

    val currentIndex: Int
        get() {
            val listState = _lazyListState ?: return pendingInitialIndex ?: 0
            val layoutInfo = listState.layoutInfo
            if (layoutInfo.visibleItemsInfo.isEmpty()) return pendingInitialIndex ?: 0

            val viewportCenter = layoutInfo.viewportEndOffset / 2
            val closestItem = layoutInfo.visibleItemsInfo.minByOrNull {
                (it.offset + it.size / 2 - viewportCenter).absoluteValue
            }

            val virtualIndex = closestItem?.index ?: return pendingInitialIndex ?: 0
            if (itemCount == 0) return 0
            return virtualIndex % itemCount
        }

    suspend fun scrollToIndex(index: Int) {
        val listState = _lazyListState ?: return
        if (itemCount == 0) return

        val currentVirtual = listState.firstVisibleItemIndex
        val currentActual = currentVirtual % itemCount

        // Find the shortest distance
        var diff = index - currentActual
        if (diff > itemCount / 2) diff -= itemCount
        if (diff < -itemCount / 2) diff += itemCount

        listState.scrollToItem(currentVirtual + diff)
    }

    suspend fun animateScrollToIndex(index: Int) {
        val listState = _lazyListState ?: return
        if (itemCount == 0) return

        val currentVirtual = listState.firstVisibleItemIndex
        val currentActual = currentVirtual % itemCount

        var diff = index - currentActual
        if (diff > itemCount / 2) diff -= itemCount
        if (diff < -itemCount / 2) diff += itemCount

        listState.animateScrollToItem(currentVirtual + diff)
    }
}

@Immutable
data class WheelPickerEffect(
    val maxRotationX: Float,
    val minScale: Float,
    val minAlpha: Float,
    val cameraDistance: Float
) {
    companion object {
        internal val Default = WheelPickerEffect(
            maxRotationX = 60f,
            minScale = 0.6f,
            minAlpha = 0.4f,
            cameraDistance = 8f
        )
    }
}

@Immutable
data class BasicWheelPickerDimens(
    val itemHeight: Dp,
    val visibleItemCount: Int,
) {
    companion object {
        internal val Pointer = BasicWheelPickerDimens(
            itemHeight = 32.dp,
            visibleItemCount = 5
        )
        internal val Touch = BasicWheelPickerDimens(
            itemHeight = 44.dp,
            visibleItemCount = 5
        )

        fun of(interactType: InteractType) = when (interactType) {
            InteractType.Pointer -> Pointer
            InteractType.Touch -> Touch
        }
    }
}

object BasicWheelPickerDefaults {
    @Composable
    fun dimens(type: InteractType = LocalInteractType.current) = BasicWheelPickerDimens.of(type)

    @Composable
    fun effect(
        maxRotationX: Float = 60f,
        minScale: Float = 0.6f,
        minAlpha: Float = 0.4f,
        cameraDistance: Float = 8f
    ) = WheelPickerEffect(maxRotationX, minScale, minAlpha, cameraDistance)
}

/**
 * Scope for [WheelPicker] item content.
 * Provides information about the item's position and state within the picker.
 */
@Stable
interface WheelPickerItemScope {
    /**
     * Whether this item is currently selected (centered).
     * This state is derived and will trigger recomposition only when it changes.
     */
    val isSelected: Boolean

    /**
     * The relative index of this item from the center.
     * 0 means centered, -1 means one item above, 1 means one item below.
     * This state is derived and will trigger recomposition only when it changes.
     */
    val index: Int
}

private class WheelPickerItemScopeImpl(
    private val listState: LazyListState,
    private val virtualIndex: Int
) : WheelPickerItemScope {
    
    private val centerIndexState = derivedStateOf {
        val layoutInfo = listState.layoutInfo
        val visibleItems = layoutInfo.visibleItemsInfo
        if (visibleItems.isEmpty()) return@derivedStateOf -1
        
        val center = layoutInfo.viewportEndOffset / 2
        val centerItem = visibleItems.minByOrNull { 
            (it.offset + it.size / 2 - center).absoluteValue 
        }
        centerItem?.index ?: -1
    }

    override val isSelected: Boolean
        get() = centerIndexState.value == virtualIndex

    override val index: Int
        get() = if (centerIndexState.value == -1) 0 else virtualIndex - centerIndexState.value
}

/**
 * Scope for [WheelPicker] content DSL.
 */
interface WheelPickerScope {
    /**
     * Add items to the wheel picker.
     *
     * @param items The collection of items to display.
     * @param key Optional key function for stable identity.
     * @param content The composable content for each item, with access to [WheelPickerItemScope].
     */
    fun <T> items(
        items: Collection<T>,
        key: (T) -> Any? = { it },
        content: @Composable WheelPickerItemScope.(T) -> Unit
    )
}

private class WheelPickerScopeImpl : WheelPickerScope {
    var itemsData: List<Any?>? = null
        private set
    var keyProvider: ((Any?) -> Any?)? = null
        private set
    var contentProvider: (@Composable WheelPickerItemScope.(Any?) -> Unit)? = null
        private set

    val count: Int get() = itemsData?.size ?: 0

    @Suppress("UNCHECKED_CAST")
    override fun <T> items(
        items: Collection<T>,
        key: (T) -> Any?,
        content: @Composable WheelPickerItemScope.(T) -> Unit
    ) {
        itemsData = items.toList()
        keyProvider = { key(it as T) }
        contentProvider = { content(it as T) }
    }
}

/**
 * A wheel picker component with infinite scrolling and a 3D curved effect.
 *
 * @param modifier The modifier to be applied to the layout.
 * @param state The state object to be used to control or observe the picker's state.
 * @param dimens The dimensions configuration for the picker.
 * @param content The content DSL for defining items.
 */
@Composable
fun WheelPicker(
    modifier: Modifier = Modifier,
    state: WheelPickerState = rememberWheelPickerState(),
    dimens: BasicWheelPickerDimens = BasicWheelPickerDefaults.dimens(),
    effect: WheelPickerEffect = BasicWheelPickerDefaults.effect(),
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    isInfinite: Boolean = true,
    userScrollEnabled: Boolean = true,
    selector: (@Composable androidx.compose.foundation.layout.BoxScope.() -> Unit)? = null,
    onScrollFinished: ((Int) -> Unit)? = null,
    content: WheelPickerScope.() -> Unit
) {
    // Build scope
    val scope = remember { WheelPickerScopeImpl() }
    scope.apply(content)

    val count = scope.count
    val itemsData = scope.itemsData
    val contentProvider = scope.contentProvider

    if (count == 0 || itemsData == null || contentProvider == null) {
        Box(modifier = modifier.height(dimens.itemHeight * dimens.visibleItemCount))
        return
    }

    // Initialize state with count (must be synchronous for first render)
    @Suppress("UNUSED_VARIABLE")
    val initialized = remember(count, state, isInfinite) {
        state.initialize(count, isInfinite)
        true
    }

    // LazyListState creation
    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = state.initialScrollIndex
    )

    // Notify scroll finished
    if (onScrollFinished != null) {
        LaunchedEffect(listState, onScrollFinished) {
            var wasInProgress = listState.isScrollInProgress
            snapshotFlow { listState.isScrollInProgress }
                .collect { inProgress ->
                    if (!inProgress && wasInProgress) {
                        onScrollFinished(state.currentIndex)
                    }
                    wasInProgress = inProgress
                }
        }
    }

    // Attach listState to WheelPickerState
    DisposableEffect(state, listState) {
        state._lazyListState = listState
        onDispose { state._lazyListState = null }
    }

    // Snap behavior
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    // Calculate container height
    val containerHeight = dimens.itemHeight * dimens.visibleItemCount

    val contentPadding = remember(dimens, isInfinite) {
        if (!isInfinite) {
            val halfCount = (dimens.visibleItemCount - 1) / 2f
            PaddingValues(vertical = dimens.itemHeight * halfCount)
        } else {
            PaddingValues(0.dp)
        }
    }

    // Support mouse drag scrolling
    val coroutineScope = rememberCoroutineScope()
    val draggableState = rememberDraggableState { delta ->
        coroutineScope.launch {
            listState.scrollBy(-delta)
        }
    }

    Box(modifier = modifier) {
        // Selector layer (below content)
        // If we want it below, we put it first. If above, last.
        // Usually selector is behind text but above background.
        // Let's assume typical iOS picker style where it might be an overlay or underlay.
        // The user request didn't specify z-order, but usually it's better as an underlay for highlights
        // or overlay for lines.
        // Since the text is 3D transformed, putting a flat overlay on top might look weird if it intersects.
        // Putting it centrally aligned in the Box.
        if (selector != null) {
            Box(
                modifier = Modifier
                    .height(dimens.itemHeight)
                    .fillMaxWidth()
                    .align(Alignment.Center),
                content = selector
            )
        }

        LazyColumn(
            modifier = Modifier
                .height(containerHeight)
                .fillMaxWidth()
                .draggable(
                    state = draggableState,
                    orientation = Orientation.Vertical,
                    enabled = userScrollEnabled,
                    onDragStopped = { velocity ->
                        if (userScrollEnabled) {
                            coroutineScope.launch {
                                // Apply fling behavior after drag ends
                                listState.scroll {
                                    with(flingBehavior) {
                                        performFling(-velocity)
                                    }
                                }
                            }
                        }
                    }
                ),
            state = listState,
            flingBehavior = flingBehavior,
            contentPadding = contentPadding,
            horizontalAlignment = horizontalAlignment,
            userScrollEnabled = userScrollEnabled
        ) {
            items(
                count = state.virtualCount,
                key = { index ->
                    val actualIndex = index % count
                    val item = itemsData[actualIndex]
                    val userKey = scope.keyProvider?.invoke(item)

                    if (userKey != null) {
                        // Combine user key with the cycle index to ensure uniqueness across the virtual list
                        userKey to (index / count)
                    } else {
                        index
                    }
                }
            ) { virtualIndex ->
                // Calculate the actual index
                val actualIndex = virtualIndex % count
                val item = itemsData[actualIndex]

                val itemScope = remember(virtualIndex, listState) {
                    WheelPickerItemScopeImpl(listState, virtualIndex)
                }

                // 3D Curved Effect
                Box(
                    modifier = Modifier
                        .height(dimens.itemHeight)
                        .fillMaxWidth()
                        .graphicsLayer {
                            val layoutInfo = listState.layoutInfo
                            val visibleItems = layoutInfo.visibleItemsInfo
                            val itemInfo = visibleItems.find { it.index == virtualIndex }

                            if (itemInfo != null) {
                                val viewportCenter = layoutInfo.viewportEndOffset / 2f
                                val itemCenter = itemInfo.offset + itemInfo.size / 2f
                                val distance = (itemCenter - viewportCenter)

                                // Normalize distance based on viewport half-height
                                val normalizedDistance = distance / (layoutInfo.viewportEndOffset / 2f)

                                // 1. Rotation X - creates the cylinder effect
                                rotationX = -effect.maxRotationX * normalizedDistance
                                cameraDistance = effect.cameraDistance

                                // 2. Scale - items at edges are smaller
                                val scale = 1f - (normalizedDistance.absoluteValue * (1f - effect.minScale))
                                scaleX = scale
                                scaleY = scale

                                // 3. Alpha - items at edges fade out
                                alpha = 1f - (normalizedDistance.absoluteValue * (1f - effect.minAlpha))
                            }
                        },
                    contentAlignment = when (horizontalAlignment) {
                        Alignment.Start -> Alignment.CenterStart
                        Alignment.End -> Alignment.CenterEnd
                        else -> Alignment.Center
                    }
                ) {
                    contentProvider(itemScope, item)
                }
            }
        }
    }
}
