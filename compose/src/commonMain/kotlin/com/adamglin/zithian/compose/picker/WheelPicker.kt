package com.adamglin.zithian.compose.picker

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.adamglin.zithian.compose.text.LocalTextStyle
import com.adamglin.zithian.compose.theme.InteractType
import com.adamglin.zithian.compose.theme.LocalContentColor
import com.adamglin.zithian.compose.theme.LocalInteractType
import com.adamglin.zithian.compose.theme.ZithianTheme
import kotlinx.coroutines.flow.collect
import kotlin.math.abs

@Immutable
data class WheelPickerDimens(
    val itemHeight: Dp,
    val visibleItemsCount: Int,
    val unselectedAlpha: Float,
    val unselectedScale: Float,
) {
    companion object {
        internal val Pointer = WheelPickerDimens(
            itemHeight = 32.dp,
            visibleItemsCount = 5,
            unselectedAlpha = 0.3f,
            unselectedScale = 0.8f,
        )

        internal val Touch = WheelPickerDimens(
            itemHeight = 40.dp,
            visibleItemsCount = 5,
            unselectedAlpha = 0.3f,
            unselectedScale = 0.8f,
        )

        fun of(interactType: InteractType): WheelPickerDimens {
            return when (interactType) {
                InteractType.Pointer -> Pointer
                InteractType.Touch -> Touch
            }
        }
    }
}

@Immutable
data class WheelPickerColors(
    val contentColor: Color,
)

object WheelPickerDefaults {
    @Composable
    fun dimens(
        interactType: InteractType = LocalInteractType.current
    ): WheelPickerDimens = WheelPickerDimens.of(interactType)

    @Composable
    fun colors(
        contentColor: Color = LocalContentColor.current,
    ): WheelPickerColors = WheelPickerColors(
        contentColor = contentColor,
    )

    @Composable
    fun textStyle(): TextStyle = ZithianTheme.typography.titleMedium
}

@Composable
fun WheelPicker(
    count: Int,
    initialIndex: Int,
    onScrollFinished: (index: Int) -> Unit,
    modifier: Modifier = Modifier,
    infinite: Boolean = true,
    dimens: WheelPickerDimens = WheelPickerDefaults.dimens(),
    colors: WheelPickerColors = WheelPickerDefaults.colors(),
    textStyle: TextStyle = WheelPickerDefaults.textStyle(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable BoxScope.(index: Int) -> Unit,
) {
    // Use a large enough number to simulate infinite scrolling, but avoid Integer overflow in layout calculations
    // (e.g. Accessibility or Pixel bounds). 100,000 items * ~100px is ~10M pixels, which fits in Int.
    val largeCount = remember(count) { 100_000.coerceAtLeast(count * 2) }
    
    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = if (infinite) {
            (largeCount / 2) - ((largeCount / 2) % count) + initialIndex
        } else {
            initialIndex
        }
    )
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)
    val currentOnScrollFinished by rememberUpdatedState(onScrollFinished)

    val itemHeightPx = with(LocalDensity.current) { dimens.itemHeight.toPx() }

    LaunchedEffect(listState) {
        snapshotFlow { listState.isScrollInProgress }
            .collect { isScrollInProgress ->
                if (!isScrollInProgress) {
                    // Find the center item
                    val layoutInfo = listState.layoutInfo
                    if (layoutInfo.visibleItemsInfo.isNotEmpty()) {
                        val centerOffset =
                            layoutInfo.viewportStartOffset + (layoutInfo.viewportEndOffset - layoutInfo.viewportStartOffset) / 2

                        var closestItemIndex = -1
                        var minDistance = Float.MAX_VALUE

                        layoutInfo.visibleItemsInfo.forEach { item ->
                            val itemCenter = item.offset + item.size / 2
                            val distance = abs(centerOffset - itemCenter)
                            if (distance < minDistance) {
                                minDistance = distance.toFloat()
                                closestItemIndex = item.index
                            }
                        }

                        if (closestItemIndex != -1) {
                            val finalIndex = if (infinite) closestItemIndex % count else closestItemIndex
                            currentOnScrollFinished(finalIndex)
                        }
                    }
                }
            }
    }

    Box(
        modifier = modifier
            .height(dimens.itemHeight * dimens.visibleItemsCount),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            state = listState,
            flingBehavior = flingBehavior,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = dimens.itemHeight * (dimens.visibleItemsCount / 2))
        ) {
            val itemCount = if (infinite) largeCount else count

            items(itemCount) { globalIndex ->
                val index = if (infinite) globalIndex % count else globalIndex

                Box(
                    modifier = Modifier
                        .height(dimens.itemHeight)
                        .fillMaxWidth()
                        .graphicsLayer {
                            val layoutInfo = listState.layoutInfo
                            val centerOffset =
                                layoutInfo.viewportStartOffset + (layoutInfo.viewportEndOffset - layoutInfo.viewportStartOffset) / 2
                            val itemInfo = layoutInfo.visibleItemsInfo.find { it.index == globalIndex }

                            if (itemInfo != null) {
                                val itemCenter = itemInfo.offset + itemInfo.size / 2
                                val distance = abs(centerOffset - itemCenter)
                                val maxDistance = (dimens.visibleItemsCount / 2) * itemHeightPx

                                // Calculate normalized distance (0 at center, 1 at edge)
                                val normalizedDistance = (distance / maxDistance).coerceIn(0f, 1f)

                                // Interpolate scale
                                val scale = 1f - (1f - dimens.unselectedScale) * normalizedDistance
                                scaleX = scale
                                scaleY = scale
                                alpha = 1f - (1f - dimens.unselectedAlpha) * normalizedDistance
                            } else {
                                scaleX = dimens.unselectedScale
                                scaleY = dimens.unselectedScale
                                alpha = dimens.unselectedAlpha
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    CompositionLocalProvider(
                        LocalContentColor provides colors.contentColor,
                        LocalTextStyle provides textStyle
                    ) {
                        content(index)
                    }
                }
            }
        }
    }
}
