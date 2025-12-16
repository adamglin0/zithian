package com.adamglin.zithian.compose.sheets

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.adamglin.composecontinuousroundedcornershape.ContinuousRoundedCornerShape
import com.adamglin.zithian.compose.scaffold.LocalSheetContainerRadius
import com.adamglin.zithian.compose.theme.ZithianTheme
import com.adamglin.zithian.compose.utils.LocalWindowRoundedCornerSize
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState

/**
 * Default horizontal padding for sheet content from screen edges.
 */
private val SheetHorizontalPadding = 13.dp

/**
 * Properties for configuring modal bottom sheet popup behavior.
 */
data class BottomSheetProperties(
    val focusable: Boolean = true,
    val dismissOnBackPress: Boolean = true,
    val dismissOnClickOutside: Boolean = true,
    val clippingEnabled: Boolean = false,
)

internal expect fun BottomSheetProperties.toPopupProperties(): PopupProperties

/**
 * Scope for the bottom sheet content.
 * Provides access to [dismiss] for closing the sheet and [containerRadius] for radius-aware styling.
 */
@Stable
interface BottomSheetContentScope : BottomSheetScope {
    /**
     * The corner radius of the sheet container.
     * This can be used by content components to create consistent styling
     * with the sheet's rounded corners.
     */
    val containerRadius: Dp
}

internal class BottomSheetContentScopeImpl(
    override val containerRadius: Dp,
    private val sheetScope: BottomSheetScope,
) : BottomSheetContentScope {
    override fun dismiss() = sheetScope.dismiss()
}

/**
 * A bottom sheet component that uses [BasicBottomSheet] for animation handling.
 *
 * This component provides a simple container for sheet content with:
 * - Positioning at the bottom of the screen
 * - Horizontal padding from screen edges
 * - Navigation bar padding
 * - Continuous rounded corner shape
 * - Content size animation
 *
 * For structured layouts with header/bottom areas, use [SheetScaffold] inside the content lambda.
 * The [containerRadius] is available through [BottomSheetContentScope] and [LocalSheetContainerRadius]
 * for radius-aware components like [SheetScaffold].
 *
 * @param onDismissRequest Callback invoked after the exit animation completes.
 *                         Use this to remove the sheet from composition.
 * @param modifier Modifier to be applied to the sheet.
 * @param backgroundColor Background color of the sheet container.
 * @param properties Properties for popup behavior configuration.
 * @param content The main content of the sheet. Has access to [BottomSheetContentScope.dismiss]
 *                and [BottomSheetContentScope.containerRadius].
 */
@Composable
fun BottomSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = ZithianTheme.colors.surface,
    properties: BottomSheetProperties = BottomSheetProperties(),
    content: @Composable BottomSheetContentScope.() -> Unit,
) {
    val radius = LocalWindowRoundedCornerSize.current - SheetHorizontalPadding

    BasicBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier
            .padding(horizontal = SheetHorizontalPadding)
            .navigationBarsPadding(),
        properties = properties,
    ) { sheetScope ->
        val shape = ContinuousRoundedCornerShape(radius)
        val hazeState = rememberHazeState()
        Box(
            modifier = Modifier
                .clip(shape)
                .background(backgroundColor, shape)
                .hazeSource(hazeState)
        ) {
            AnimatedContent(Unit) {
                Box(modifier = Modifier.animateContentSize()) {
                    CompositionLocalProvider(LocalSheetContainerRadius provides radius) {
                        with(BottomSheetContentScopeImpl(radius, sheetScope)) {
                            content()
                        }
                    }
                }
            }
        }
    }
}
