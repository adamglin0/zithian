package com.adamglin.zithian.compose.scaffold

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.adamglin.zithian.compose.theme.ZithianTheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import io.github.fletchmckee.liquid.LiquidState
import io.github.fletchmckee.liquid.liquefiable
import io.github.fletchmckee.liquid.rememberLiquidState

/**
 * CompositionLocal to provide the container radius to sheet content.
 * This allows any component within the sheet to access the radius value.
 */
val LocalSheetContainerRadius = compositionLocalOf<Dp> { 0.dp }

/**
 * Scope for SheetScaffold that extends [ScaffoldScope] and provides
 * access to the container radius for radius-aware components.
 */
@Stable
interface SheetScaffoldScope : ScaffoldScope {
    /**
     * The corner radius of the sheet container.
     * This can be used by header, bottom, and content components
     * to create consistent styling with the sheet's rounded corners.
     */
    val containerRadius: Dp
}

/**
 * Internal implementation of [SheetScaffoldScope].
 */
@Stable
internal class SheetScaffoldScopeImpl(
    override val containerRadius: Dp,
    override val hazeState: HazeState,
    override val liquidState: LiquidState,
) : SheetScaffoldScope {
    private var _headerHeight by mutableStateOf(0.dp)
    override var headerHeight: Dp
        get() = _headerHeight
        set(value) {
            _headerHeight = value
        }

    private var _bottomHeight by mutableStateOf(0.dp)
    override var bottomHeight: Dp
        get() = _bottomHeight
        set(value) {
            _bottomHeight = value
        }
}

/**
 * Creates and remembers a [SheetScaffoldScope] instance.
 *
 * @param containerRadius The corner radius of the sheet container.
 * @return A remembered [SheetScaffoldScope] instance.
 */
@Composable
fun rememberSheetScaffoldScope(containerRadius: Dp): SheetScaffoldScope {
    val hazeState = rememberHazeState()
    val liquidState = rememberLiquidState()
    return remember(containerRadius) {
        SheetScaffoldScopeImpl(containerRadius, hazeState, liquidState)
    }
}

/**
 * A scaffold component designed specifically for sheet layouts (e.g., bottom sheets).
 *
 * This component extends the functionality of [BasicScaffold] by providing
 * the container radius to all slot components through [SheetScaffoldScope].
 * This enables header, bottom, and content components to create styling
 * that is aware of and consistent with the sheet's rounded corners.
 *
 * Key features:
 * - Provides [containerRadius] through [SheetScaffoldScope] to all slots
 * - Also provides [containerRadius] through [LocalSheetContainerRadius] for nested components
 * - Handles header/bottom/content layout measurement and placement
 * - Integrates with haze and liquid effects
 *
 * @param containerRadius The corner radius of the sheet container.
 * @param modifier Modifier to be applied to the scaffold.
 * @param header Optional header content displayed at the top of the sheet.
 *               Has access to [SheetScaffoldScope.containerRadius] for radius-aware styling.
 * @param bottom Optional bottom content displayed at the bottom of the sheet.
 *               Has access to [SheetScaffoldScope.containerRadius] for radius-aware styling.
 * @param backgroundColor Background color of the scaffold.
 * @param content The main content of the sheet.
 *                Has access to [SheetScaffoldScope.containerRadius] for radius-aware styling.
 */
@Composable
fun SheetScaffold(
    containerRadius: Dp,
    modifier: Modifier = Modifier,
    header: (@Composable SheetScaffoldScope.() -> Unit)? = null,
    bottom: (@Composable SheetScaffoldScope.() -> Unit)? = null,
    backgroundColor: Color = ZithianTheme.colors.surface,
    content: @Composable SheetScaffoldScope.() -> Unit,
) {
    val headerNotNull = header ?: {}
    val bottomNotNull = bottom ?: {}

    CompositionLocalProvider(LocalSheetContainerRadius provides containerRadius) {
        Box(
            modifier = modifier.background(backgroundColor)
        ) {
            val sheetScaffoldScope = rememberSheetScaffoldScope(containerRadius)
            if (header == null && bottom == null) {
                SheetContentWrapper(sheetScaffoldScope, backgroundColor) {
                    content()
                }
                return@Box
            }
            SubcomposeLayout(Modifier) { constraints ->
                val headerPlaceables = subcompose("header") {
                    headerNotNull(sheetScaffoldScope)
                }.map {
                    it.measure(constraints)
                }
                val headerHeight = headerPlaceables.maxOfOrNull { it.height } ?: 0
                sheetScaffoldScope.headerHeight = headerHeight.toDp()

                val contentPlaceables = subcompose("content") {
                    SheetContentWrapper(sheetScaffoldScope, backgroundColor) {
                        content()
                    }
                }.map { it.measure(constraints) }

                val bottomPlaceables = subcompose("bottom") {
                    bottomNotNull(sheetScaffoldScope)
                }.map {
                    it.measure(constraints)
                }
                val bottomHeight = bottomPlaceables.maxOfOrNull { it.height } ?: 0
                sheetScaffoldScope.bottomHeight = bottomHeight.toDp()

                val contentHeight = contentPlaceables.maxOfOrNull { it.height } ?: 0
                layout(constraints.maxWidth, maxOf(headerHeight + bottomHeight, contentHeight)) {
                    contentPlaceables.forEach { it.placeRelative(0, 0) }
                    headerPlaceables.forEach { it.placeRelative(0, 0) }
                    bottomPlaceables.forEach {
                        it.placeRelative(0, constraints.maxHeight - bottomHeight)
                    }
                }
            }
        }
    }
}

@Composable
private fun SheetContentWrapper(
    sheetScaffoldScope: SheetScaffoldScope,
    backgroundColor: Color,
    content: @Composable SheetScaffoldScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .hazeSource(sheetScaffoldScope.hazeState)
            .background(backgroundColor)
            .liquefiable(sheetScaffoldScope.liquidState),
    ) {
        content(sheetScaffoldScope)
    }
}
