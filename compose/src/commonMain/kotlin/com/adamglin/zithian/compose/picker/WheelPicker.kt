package com.adamglin.zithian.compose.picker

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@Composable
fun rememberWheelPickerState(
    initialIndex: Int = 0,
    onIndexChange: (index: Int) -> Unit = {}
): WheelPickerState {
    return rememberSaveable(saver = WheelPickerState.Saver(onIndexChange)) {
        WheelPickerState(initialIndex, onIndexChange)
    }
}

@Stable
class WheelPickerState(
    private val initialIndex: Int = 0,
    val onIndexChange: (index: Int) -> Unit = {}
) {
    internal var _lazyListState: LazyListState? = null
    val lazyListState: LazyListState
        get() = _lazyListState ?: error("WheelPickerState is not attached to a WheelPicker")

    // Use a reasonable multiplier to simulate infinite scrolling
    // This provides ~5000 full cycles in each direction which is more than enough
    internal companion object {
        const val VIRTUAL_MULTIPLIER = 10000

        fun Saver(onIndexChange: (index: Int) -> Unit) =
            androidx.compose.runtime.saveable.Saver<WheelPickerState, Int>(
                save = {
                    it.currentIndex
                },
                restore = { WheelPickerState(it, onIndexChange) }
            )
    }

    // Calculated in attach to ensure alignment
    internal var initialScrollIndex = 0
    internal var virtualCount = 0
        private set
    private var itemCount = 0
    private var isInfiniteMode = true

    internal fun initialize(count: Int, isInfinite: Boolean) {
        itemCount = count
        isInfiniteMode = isInfinite
        if (count > 0) {
            if (isInfinite) {
                virtualCount = count * VIRTUAL_MULTIPLIER
                val infiniteCenter = virtualCount / 2
                val offset = infiniteCenter % count
                initialScrollIndex = infiniteCenter - offset + initialIndex
            } else {
                // In finite mode, add spacer items at beginning and end
                // to allow first/last items to scroll to center
                virtualCount = count + 2  // +2 for top and bottom spacers
                // Position the initial item at the center (offset by 1 for top spacer)
                initialScrollIndex = initialIndex.coerceIn(0, count - 1) + 1
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

            return if (isInfiniteMode) {
                virtualIndex % itemCount
            } else {
                // In finite mode, virtualIndex includes spacers at start (1) and end
                // Map virtualIndex [0, count+1] to actualIndex [0, count-1]
                val adjustedIndex = virtualIndex - 1  // Offset for top spacer
                adjustedIndex.coerceIn(0, itemCount - 1)
            }
        }

    suspend fun scrollToIndex(index: Int) {
        val listState = _lazyListState ?: return
        if (itemCount == 0) return

        if (isInfiniteMode) {
            val currentVirtual = listState.firstVisibleItemIndex
            val currentActual = currentVirtual % itemCount

            // Find the shortest distance
            var diff = index - currentActual
            if (diff > itemCount / 2) diff -= itemCount
            if (diff < -itemCount / 2) diff += itemCount

            listState.scrollToItem(currentVirtual + diff)
        } else {
            // In finite mode, offset by 1 for the top spacer
            listState.scrollToItem(index.coerceIn(0, itemCount - 1) + 1)
        }
    }

    suspend fun animateScrollToIndex(index: Int) {
        val listState = _lazyListState ?: return
        if (itemCount == 0) return

        if (isInfiniteMode) {
            val currentVirtual = listState.firstVisibleItemIndex
            val currentActual = currentVirtual % itemCount

            var diff = index - currentActual
            if (diff > itemCount / 2) diff -= itemCount
            if (diff < -itemCount / 2) diff += itemCount

            listState.animateScrollToItem(currentVirtual + diff)
        } else {
            // In finite mode, offset by 1 for the top spacer
            listState.animateScrollToItem(index.coerceIn(0, itemCount - 1) + 1)
        }
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
        val Default = WheelPickerEffect(
            maxRotationX = 60f,
            minScale = 0.6f,
            minAlpha = 0.4f,
            cameraDistance = 8f
        )
    }
}

object WheelPickerDefaults {
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
 * Items are laid out based on their intrinsic size. The picker height should be
 * specified via the [modifier] parameter (e.g., `Modifier.height(200.dp)`).
 *
 * @param modifier The modifier to be applied to the layout. Use `Modifier.height()` to set picker height.
 * @param state The state object to be used to control or observe the picker's state.
 * @param effect The 3D effect configuration for the picker.
 * @param horizontalAlignment The horizontal alignment of items within the picker.
 * @param verticalArrangement The vertical arrangement of items within the picker.
 * @param isInfinite Whether the picker should scroll infinitely.
 * @param userScrollEnabled Whether the user can scroll the picker.
 * @param hapticFeedbackEnabled Whether to perform haptic feedback when the selected item changes (iOS-like tick effect).
 * @param selector Optional composable to display as a selection indicator.
 * @param onScrollFinished Callback invoked when scrolling finishes with the selected index.
 * @param content The content DSL for defining items.
 */
@Composable
fun WheelPicker(
    modifier: Modifier = Modifier,
    state: WheelPickerState = rememberWheelPickerState(),
    effect: WheelPickerEffect = WheelPickerDefaults.effect(),
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    isInfinite: Boolean = true,
    userScrollEnabled: Boolean = true,
    hapticFeedbackEnabled: Boolean = true,
    selector: (@Composable BoxScope.() -> Unit)? = null,
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
        Box(modifier = modifier)
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

    // Haptic feedback on index change
    val hapticFeedback = LocalHapticFeedback.current
    LaunchedEffect(hapticFeedbackEnabled, listState) {
        if (!hapticFeedbackEnabled) return@LaunchedEffect
        var previousIndex: Int? = null
        snapshotFlow { state.currentIndex }
            .collect { currentIndex ->
                if (previousIndex != null && previousIndex != currentIndex) {
                    hapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                }
                previousIndex = currentIndex
            }
    }

    // Invoke onIndexChange callback
    LaunchedEffect(state) {
        snapshotFlow { state.currentIndex }
            .collect { index ->
                state.onIndexChange(index)
            }
    }

    // Handle scroll finished callback
    LaunchedEffect(listState, state, onScrollFinished) {
        var wasInProgress = listState.isScrollInProgress
        snapshotFlow { listState.isScrollInProgress }
            .collect { inProgress ->
                if (!inProgress && wasInProgress) {
                    onScrollFinished?.invoke(state.currentIndex)
                }
                wasInProgress = inProgress
            }
    }

    // Attach listState to WheelPickerState
    DisposableEffect(state, listState) {
        state._lazyListState = listState
        onDispose { state._lazyListState = null }
    }

    // Snap behavior
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    // Support mouse drag scrolling
    val coroutineScope = rememberCoroutineScope()
    val draggableState = rememberDraggableState { delta ->
        coroutineScope.launch {
            listState.scrollBy(-delta)
        }
    }

    BoxWithConstraints(modifier = modifier) {
        // In finite mode, spacers are used instead of content padding
        val contentPadding = PaddingValues()
        // Capture maxHeight for use in item lambda
        val pickerHeight = maxHeight

        // Selector layer - centered in the picker
        if (selector != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                content = selector
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
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
            verticalArrangement = verticalArrangement,
            userScrollEnabled = userScrollEnabled
        ) {
            items(
                count = state.virtualCount,
                key = { index ->
                    if (isInfinite) {
                        val actualIndex = index % count
                        val item = itemsData[actualIndex]
                        val userKey = scope.keyProvider?.invoke(item)
                        if (userKey != null) {
                            // Combine user key with the cycle index to ensure uniqueness across the virtual list
                            userKey to (index / count)
                        } else {
                            index
                        }
                    } else {
                        // In finite mode, include offset for spacers in key
                        index
                    }
                }
            ) { virtualIndex ->
                // In finite mode, handle spacer items
                if (!isInfinite) {
                    // First item is top spacer, last item is bottom spacer
                    if (virtualIndex == 0 || virtualIndex == state.virtualCount - 1) {
                        // Spacer item - provide half-height spacing to center first/last items
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = when (horizontalAlignment) {
                                Alignment.Start -> Alignment.CenterStart
                                Alignment.End -> Alignment.CenterEnd
                                else -> Alignment.Center
                            }
                        ) {
                            // Use Spacer to take up half the viewport height
                            // This allows first/actual items to scroll to center
                            Spacer(
                                modifier = Modifier.height(pickerHeight / 2)
                            )
                        }
                        return@items
                    }
                }

                // Calculate the actual index
                val actualIndex = if (isInfinite) {
                    virtualIndex % count
                } else {
                    virtualIndex - 1  // Offset for top spacer
                }
                val item = itemsData[actualIndex]

                val itemScope = remember(virtualIndex, listState) {
                    WheelPickerItemScopeImpl(listState, virtualIndex)
                }

                // 3D Curved Effect
                Box(
                    modifier = Modifier
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

                                // Transform origin should match horizontal alignment
                                transformOrigin = when (horizontalAlignment) {
                                    Alignment.Start -> TransformOrigin(0f, 0.5f)
                                    Alignment.End -> TransformOrigin(1f, 0.5f)
                                    else -> TransformOrigin(0.5f, 0.5f)
                                }

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
