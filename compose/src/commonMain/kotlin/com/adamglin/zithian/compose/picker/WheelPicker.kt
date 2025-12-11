package com.adamglin.zithian.compose.picker

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
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

    internal fun initialize(count: Int) {
        itemCount = count
        if (count > 0) {
            virtualCount = count * VIRTUAL_MULTIPLIER
            val infiniteCenter = virtualCount / 2
            val offset = infiniteCenter % count
            initialScrollIndex = infiniteCenter - offset + initialIndex
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
     * @param content The composable content for each item.
     */
    fun <T> items(
        items: Collection<T>,
        key: (T) -> Any? = { it },
        content: @Composable (T) -> Unit
    )
}

private class WheelPickerScopeImpl : WheelPickerScope {
    var itemsData: List<Any?>? = null
        private set
    var keyProvider: ((Any?) -> Any?)? = null
        private set
    var contentProvider: (@Composable (Any?) -> Unit)? = null
        private set

    val count: Int get() = itemsData?.size ?: 0

    @Suppress("UNCHECKED_CAST")
    override fun <T> items(
        items: Collection<T>,
        key: (T) -> Any?,
        content: @Composable (T) -> Unit
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
    val initialized = remember(count, state) {
        state.initialize(count)
        true
    }

    // LazyListState creation
    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = state.initialScrollIndex
    )

    // Attach listState to WheelPickerState
    DisposableEffect(state, listState) {
        state._lazyListState = listState
        onDispose { state._lazyListState = null }
    }

    // Snap behavior
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    // Calculate container height
    val containerHeight = dimens.itemHeight * dimens.visibleItemCount

    // Support mouse drag scrolling
    val coroutineScope = rememberCoroutineScope()
    val draggableState = rememberDraggableState { delta ->
        coroutineScope.launch {
            listState.scrollBy(-delta)
        }
    }

    LazyColumn(
        modifier = modifier
            .height(containerHeight)
            .draggable(
                state = draggableState,
                orientation = Orientation.Vertical,
                onDragStopped = { velocity ->
                    coroutineScope.launch {
                        // Apply fling behavior after drag ends
                        listState.scroll {
                            with(flingBehavior) {
                                performFling(-velocity)
                            }
                        }
                    }
                }
            ),
        state = listState,
        flingBehavior = flingBehavior,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(
            count = state.virtualCount,
        ) { virtualIndex ->
            // Calculate the actual index
            val actualIndex = virtualIndex % count
            val item = itemsData[actualIndex]

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
                            rotationX = -60f * normalizedDistance

                            // 2. Scale - items at edges are smaller
                            val scale = 1f - (normalizedDistance.absoluteValue * 0.4f)
                            scaleX = scale
                            scaleY = scale

                            // 3. Alpha - items at edges fade out
                            alpha = 1f - (normalizedDistance.absoluteValue * 0.6f)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                contentProvider(item)
            }
        }
    }
}
