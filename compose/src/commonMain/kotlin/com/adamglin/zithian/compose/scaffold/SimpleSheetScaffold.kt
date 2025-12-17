package com.adamglin.zithian.compose.scaffold

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.adamglin.zithian.compose.annotation.InteractTypeOnly
import com.adamglin.zithian.compose.generated.resources.ZithianResources
import com.adamglin.zithian.compose.icon.CoilIcon
import com.adamglin.zithian.compose.icon.NeutralIconButton
import com.adamglin.zithian.compose.sheets.BottomSheetScope
import com.adamglin.zithian.compose.theme.InteractType
import com.adamglin.zithian.compose.theme.ZithianTheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import io.github.fletchmckee.liquid.LiquidState
import io.github.fletchmckee.liquid.liquefiable
import io.github.fletchmckee.liquid.rememberLiquidState

/**
 * Scope for SimpleSheetScaffold that provides radius-aware styling values.
 *
 * This scope is used with [SimpleSheetScaffold] where content overlays with
 * an optional floating close button in the top-right corner.
 *
 * It extends [BottomSheetScope] to allow dismissal from within the scope.
 */
@Stable
interface SimpleSheetScaffoldScope : BottomSheetScope {
    /**
     * The corner radius of the sheet container.
     */
    val containerRadius: Dp

    /**
     * The padding from the sheet edge to the close button edge.
     *
     * This value represents the distance between the close button's outer edge
     * and the sheet container's edge. Content can use this to:
     * - Add consistent padding that aligns with the close button's position
     * - Create visually balanced layouts that respect the close button's margins
     *
     * When no close button is present, this defaults to `containerRadius / 2`.
     */
    val closeButtonEdgePadding: Dp

    /**
     * Haze state for blur effects.
     */
    val hazeState: HazeState

    /**
     * Liquid state for liquid animations.
     */
    val liquidState: LiquidState
}

@Stable
internal class SimpleSheetScaffoldScopeImpl(
    override val containerRadius: Dp,
    override val hazeState: HazeState,
    override val liquidState: LiquidState,
    private val bottomSheetScope: BottomSheetScope,
) : SimpleSheetScaffoldScope, BottomSheetScope by bottomSheetScope {
    private var _closeButtonEdgePadding by mutableStateOf(0.dp)
    override var closeButtonEdgePadding: Dp
        get() = _closeButtonEdgePadding
        internal set(value) {
            _closeButtonEdgePadding = value
        }
}

@Composable
internal fun rememberSimpleSheetScaffoldScope(
    containerRadius: Dp,
    bottomSheetScope: BottomSheetScope,
): SimpleSheetScaffoldScopeImpl {
    val hazeState = rememberHazeState()
    val liquidState = rememberLiquidState()
    return remember(containerRadius, bottomSheetScope) {
        SimpleSheetScaffoldScopeImpl(containerRadius, hazeState, liquidState, bottomSheetScope)
    }
}

/**
 * A simple sheet scaffold with an optional floating close button in the top-right corner.
 *
 * Unlike [SheetScaffold] which has separate header/bottom/content areas,
 * this scaffold provides a simpler layout where content fills the entire area
 * and the close button floats on top of the content.
 *
 * The close button position follows the same visual guidelines as [TitleAndCloseSheetTitle]:
 * - Vertically centered within `containerRadius * 2` from the top
 * - Horizontally positioned with the same padding from the right edge
 *
 * Content receives [SimpleSheetScaffoldScope.closeButtonEdgePadding] which represents
 * the distance from the sheet edge to the close button's edge. This allows content to:
 * - Add appropriate padding that aligns with the close button's visual margins
 * - Create visually consistent layouts that respect the close button's presence
 *
 * It must be used within a [BottomSheetScope], ensuring it's only used inside a sheet.
 *
 * @param containerRadius The corner radius of the sheet container.
 * @param modifier Modifier to be applied to the scaffold.
 * @param onClose Optional callback for the close button. If null, no close button is shown.
 * @param backgroundColor Background color of the scaffold.
 * @param content The main content of the sheet. Has access to [SimpleSheetScaffoldScope]
 *                which provides [containerRadius] and [closeButtonEdgePadding] for layout guidance.
 */
@Composable
@InteractTypeOnly(InteractType.Touch)
fun BottomSheetScope.SimpleSheetScaffold(
    containerRadius: Dp,
    modifier: Modifier = Modifier,
    onClose: (() -> Unit)? = null,
    backgroundColor: Color = ZithianTheme.colors.surface,
    content: @Composable SimpleSheetScaffoldScope.() -> Unit,
) {
    val scope = rememberSimpleSheetScaffoldScope(containerRadius, this)

    CompositionLocalProvider(LocalSheetContainerRadius provides containerRadius) {
        SubcomposeLayout(
            modifier = modifier
                .fillMaxWidth()
                .background(backgroundColor),
        ) { constraints ->
            val containerRadiusPx = containerRadius.roundToPx()
            val headerAreaHeight = containerRadiusPx * 2

            // 1. Measure close button first (if present)
            val closePlaceables = onClose?.let { onCloseCallback ->
                subcompose("close") {
                    NeutralIconButton(onClick = onCloseCallback) {
                        CoilIcon(
                            uri = ZithianResources.getUri("drawable/ic_x.svg"),
                            contentDescription = null,
                        )
                    }
                }.map {
                    it.measure(Constraints(maxWidth = constraints.maxWidth, maxHeight = headerAreaHeight))
                }
            }
            val closePlaceable = closePlaceables?.firstOrNull()
            val closeWidth = closePlaceable?.width ?: 0
            val closeHeight = closePlaceable?.height ?: 0

            // 2. Calculate close button edge padding: (headerAreaHeight - closeHeight) / 2
            // This is the distance from the sheet edge to the close button's edge
            val closeEdgePaddingPx = if (closePlaceable != null) {
                (headerAreaHeight - closeHeight) / 2
            } else {
                containerRadiusPx / 2 // Default padding when no close button
            }
            scope.closeButtonEdgePadding = closeEdgePaddingPx.toDp()

            // 3. Subcompose content with the calculated scope
            val contentPlaceables = subcompose("content") {
                Box(
                    modifier = Modifier
                        .hazeSource(scope.hazeState)
                        .background(backgroundColor)
                        .liquefiable(scope.liquidState)
                ) {
                    content(scope)
                }
            }.map { it.measure(constraints) }

            // 4. Layout
            val totalHeight = contentPlaceables.maxOfOrNull { it.height } ?: 0
            layout(constraints.maxWidth, totalHeight) {
                // Place content first (bottom layer)
                contentPlaceables.forEach { it.placeRelative(0, 0) }

                // Place close button on top (top-right corner)
                closePlaceable?.placeRelative(
                    x = constraints.maxWidth - closeWidth - closeEdgePaddingPx,
                    y = closeEdgePaddingPx
                )
            }
        }
    }
}
