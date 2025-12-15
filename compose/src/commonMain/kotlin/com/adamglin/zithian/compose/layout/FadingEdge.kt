package com.adamglin.zithian.compose.layout

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.min

/**
 * Configuration for [FadingEdge] effect.
 *
 * @param fadeLength The maximum length of the fade gradient at each edge.
 */
@Immutable
data class FadingEdgeConfig(
    val fadeLength: Dp = 32.dp,
)

/**
 * Defaults for [FadingEdge] component.
 */
object FadingEdgeDefaults {
    /**
     * Creates a default [FadingEdgeConfig].
     *
     * @param fadeLength The maximum length of the fade gradient at each edge.
     */
    @Composable
    fun config(
        fadeLength: Dp = 32.dp,
    ): FadingEdgeConfig = FadingEdgeConfig(
        fadeLength = fadeLength,
    )
}

/**
 * Internal class to hold scroll offset information for fading edge calculations.
 */
private data class ScrollOffsets(
    val startOffset: Float,
    val endOffset: Float,
)

/**
 * Extracts scroll offset information from different types of [ScrollableState].
 */
@Composable
private fun rememberScrollOffsets(
    scrollableState: ScrollableState,
    orientation: Orientation,
): ScrollOffsets {
    val offsets by remember(scrollableState, orientation) {
        derivedStateOf {
            when (scrollableState) {
                is ScrollState -> {
                    val maxScroll = scrollableState.maxValue.toFloat()
                    val currentScroll = scrollableState.value.toFloat()
                    ScrollOffsets(
                        startOffset = currentScroll,
                        endOffset = (maxScroll - currentScroll).coerceAtLeast(0f)
                    )
                }

                is LazyListState -> {
                    val layoutInfo = scrollableState.layoutInfo
                    val visibleItems = layoutInfo.visibleItemsInfo
                    val totalItems = layoutInfo.totalItemsCount

                    if (visibleItems.isEmpty() || totalItems == 0) {
                        ScrollOffsets(0f, 0f)
                    } else {
                        val firstItem = visibleItems.first()
                        val lastItem = visibleItems.last()

                        // Calculate start offset (how far we've scrolled from the beginning)
                        val startOffset = if (firstItem.index == 0) {
                            // First item is visible, use its actual offset
                            (-firstItem.offset).toFloat().coerceAtLeast(0f)
                        } else {
                            // First item is not visible, we're scrolled past it
                            Float.MAX_VALUE
                        }

                        // Calculate end offset (how much we can still scroll)
                        val endOffset = if (lastItem.index == totalItems - 1) {
                            // Last item is visible
                            val viewportEnd = layoutInfo.viewportEndOffset
                            val lastItemEnd = lastItem.offset + lastItem.size
                            (lastItemEnd - viewportEnd).toFloat().coerceAtLeast(0f)
                        } else {
                            // More items to scroll
                            Float.MAX_VALUE
                        }

                        ScrollOffsets(startOffset, endOffset)
                    }
                }

                is LazyGridState -> {
                    val layoutInfo = scrollableState.layoutInfo
                    val visibleItems = layoutInfo.visibleItemsInfo
                    val totalItems = layoutInfo.totalItemsCount

                    if (visibleItems.isEmpty() || totalItems == 0) {
                        ScrollOffsets(0f, 0f)
                    } else {
                        val firstItem = visibleItems.first()
                        val lastItem = visibleItems.last()

                        // For grid, we check the main axis offset
                        val mainAxisOffset: (androidx.compose.foundation.lazy.grid.LazyGridItemInfo) -> Int =
                            if (orientation == Orientation.Vertical) {
                                { it.offset.y }
                            } else {
                                { it.offset.x }
                            }

                        val mainAxisSize: (androidx.compose.foundation.lazy.grid.LazyGridItemInfo) -> Int =
                            if (orientation == Orientation.Vertical) {
                                { it.size.height }
                            } else {
                                { it.size.width }
                            }

                        val viewportEnd = if (orientation == Orientation.Vertical) {
                            layoutInfo.viewportEndOffset
                        } else {
                            layoutInfo.viewportEndOffset
                        }

                        // Find the first row's items (for start offset)
                        val firstRowIndex = firstItem.index
                        val startOffset = if (firstRowIndex == 0) {
                            (-mainAxisOffset(firstItem)).toFloat().coerceAtLeast(0f)
                        } else {
                            Float.MAX_VALUE
                        }

                        // Find if we're at the end
                        val endOffset = if (lastItem.index >= totalItems - 1) {
                            val lastItemEnd = mainAxisOffset(lastItem) + mainAxisSize(lastItem)
                            (lastItemEnd - viewportEnd).toFloat().coerceAtLeast(0f)
                        } else {
                            Float.MAX_VALUE
                        }

                        ScrollOffsets(startOffset, endOffset)
                    }
                }

                else -> {
                    // For other ScrollableState implementations, assume we can scroll in both directions
                    // This is a fallback that shows full fade on both edges
                    if (scrollableState.canScrollBackward || scrollableState.canScrollForward) {
                        ScrollOffsets(
                            startOffset = if (scrollableState.canScrollBackward) Float.MAX_VALUE else 0f,
                            endOffset = if (scrollableState.canScrollForward) Float.MAX_VALUE else 0f
                        )
                    } else {
                        ScrollOffsets(0f, 0f)
                    }
                }
            }
        }
    }
    return offsets
}

/**
 * A wrapper composable that adds fading edge effects to scrollable content.
 *
 * The fading edges appear at the start and end of the scrollable area (top/bottom for vertical,
 * left/right for horizontal). The fade effect intensity dynamically adjusts based on the
 * scroll position - when near the beginning or end, the fade gradually disappears.
 *
 * @param scrollableState The [ScrollableState] to observe for scroll position.
 *                        Supports [ScrollState], [LazyListState], [LazyGridState], etc.
 * @param modifier The modifier to be applied to the layout.
 * @param orientation The scroll orientation ([Orientation.Vertical] or [Orientation.Horizontal]).
 * @param config The configuration for the fading edge effect. See [FadingEdgeDefaults.config].
 * @param content The scrollable content to wrap.
 *
 * Example usage:
 * ```
 * val scrollState = rememberScrollState()
 * FadingEdge(
 *     scrollableState = scrollState,
 *     orientation = Orientation.Vertical,
 *     config = FadingEdgeDefaults.config(fadeLength = 48.dp)
 * ) {
 *     Column(modifier = Modifier.verticalScroll(scrollState)) {
 *         // Content
 *     }
 * }
 * ```
 */
@Composable
fun FadingEdge(
    scrollableState: ScrollableState,
    modifier: Modifier = Modifier,
    orientation: Orientation = Orientation.Vertical,
    config: FadingEdgeConfig = FadingEdgeDefaults.config(),
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current
    val fadeLengthPx = with(density) { config.fadeLength.toPx() }
    val scrollOffsets = rememberScrollOffsets(scrollableState, orientation)

    // Use graphicsLayer with Offscreen compositing to enable BlendMode
    val fadingEdgeModifier = Modifier
        .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
        .drawWithContent {
            drawContent()

            val isVertical = orientation == Orientation.Vertical
            val viewportSize = if (isVertical) size.height else size.width

            // Calculate the actual fade lengths based on scroll position
            // The fade should diminish as we approach the edge
            val startFadeLength = min(fadeLengthPx, scrollOffsets.startOffset)
            val endFadeLength = min(fadeLengthPx, scrollOffsets.endOffset)

            // Draw start (top/left) fade gradient using DstOut to make content transparent
            if (startFadeLength > 0f) {
                val startGradient = if (isVertical) {
                    Brush.verticalGradient(
                        colors = listOf(Color.Black, Color.Transparent),
                        startY = 0f,
                        endY = startFadeLength
                    )
                } else {
                    Brush.horizontalGradient(
                        colors = listOf(Color.Black, Color.Transparent),
                        startX = 0f,
                        endX = startFadeLength
                    )
                }

                val startSize = if (isVertical) {
                    Size(size.width, startFadeLength)
                } else {
                    Size(startFadeLength, size.height)
                }

                drawRect(
                    brush = startGradient,
                    topLeft = Offset.Zero,
                    size = startSize,
                    blendMode = BlendMode.DstOut
                )
            }

            // Draw end (bottom/right) fade gradient using DstOut to make content transparent
            if (endFadeLength > 0f) {
                val endGradient = if (isVertical) {
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                        startY = viewportSize - endFadeLength,
                        endY = viewportSize
                    )
                } else {
                    Brush.horizontalGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                        startX = viewportSize - endFadeLength,
                        endX = viewportSize
                    )
                }

                val endOffset = if (isVertical) {
                    Offset(0f, viewportSize - endFadeLength)
                } else {
                    Offset(viewportSize - endFadeLength, 0f)
                }

                val endSize = if (isVertical) {
                    Size(size.width, endFadeLength)
                } else {
                    Size(endFadeLength, size.height)
                }

                drawRect(
                    brush = endGradient,
                    topLeft = endOffset,
                    size = endSize,
                    blendMode = BlendMode.DstOut
                )
            }
        }

    androidx.compose.foundation.layout.Box(modifier = modifier.then(fadingEdgeModifier)) {
        content()
    }
}

