package com.adamglin.zithian.compose.sheets

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.adamglin.composecontinuousroundedcornershape.ContinuousRoundedCornerShape
import com.adamglin.zithian.compose.scaffold.BasicScaffold
import com.adamglin.zithian.compose.scaffold.ScaffoldScope
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
 * Scope for the bottom sheet header slot.
 * Extends [BasicSheetHeaderScope] for compatibility with existing extension functions
 * like [TitleAndCloseSheetTitle], and [BottomSheetScope] to provide access to [dismiss].
 */
@Stable
interface BottomSheetHeaderScope : BasicSheetHeaderScope, BottomSheetScope

internal class BottomSheetHeaderScopeImpl(
    scaffoldScope: ScaffoldScope,
    override val containerRadius: Dp,
    private val sheetScope: BottomSheetScope,
) : BottomSheetHeaderScope, ScaffoldScope by scaffoldScope {
    override fun dismiss() = sheetScope.dismiss()
}

/**
 * Scope for the bottom sheet bottom slot.
 * Extends [BasicSheetBottomScope] for compatibility with existing extension functions,
 * and [BottomSheetScope] to provide access to [dismiss].
 */
@Stable
interface BottomSheetBottomScope : BasicSheetBottomScope, BottomSheetScope

internal class BottomSheetBottomScopeImpl(
    scaffoldScope: ScaffoldScope,
    override val radius: Dp,
    private val sheetScope: BottomSheetScope,
) : BottomSheetBottomScope, ScaffoldScope by scaffoldScope {
    override fun dismiss() = sheetScope.dismiss()
}

/**
 * Scope for the bottom sheet content slot.
 * Extends [ScaffoldScope] and [BottomSheetScope] to provide access to [dismiss].
 */
@Stable
interface BottomSheetContentScope : ScaffoldScope, BottomSheetScope

internal class BottomSheetContentScopeImpl(
    scaffoldScope: ScaffoldScope,
    private val sheetScope: BottomSheetScope,
) : BottomSheetContentScope, ScaffoldScope by scaffoldScope {
    override fun dismiss() = sheetScope.dismiss()
}

/**
 * A bottom sheet component that uses [BasicBottomSheet] for animation handling
 * and provides layout with header/bottom slots.
 *
 * This component handles:
 * - Positioning at the bottom of the screen
 * - Horizontal padding from screen edges
 * - Navigation bar padding
 * - Continuous rounded corner shape
 * - Header and bottom slot areas
 * - Content size animation
 *
 * All scope lambdas ([header], [bottom], [content]) have access to [BottomSheetScope.dismiss]
 * which triggers the exit animation and calls [onDismissRequest] upon completion.
 *
 * @param onDismissRequest Callback invoked after the exit animation completes.
 *                         Use this to remove the sheet from composition.
 * @param modifier Modifier to be applied to the sheet.
 * @param header Optional header content displayed at the top of the sheet.
 * @param bottom Optional bottom content displayed at the bottom of the sheet.
 * @param backgroundColor Background color of the sheet container.
 * @param properties Properties for popup behavior configuration.
 * @param content The main content of the sheet.
 */
@Composable
fun BottomSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    header: (@Composable BottomSheetHeaderScope.() -> Unit)? = null,
    bottom: (@Composable BottomSheetBottomScope.() -> Unit)? = null,
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
                    BasicScaffold(
                        backgroundColor = backgroundColor,
                        header = {
                            header?.let { headerContent ->
                                with(BottomSheetHeaderScopeImpl(this, radius, sheetScope)) {
                                    headerContent()
                                }
                            }
                        },
                        bottom = {
                            bottom?.let { bottomContent ->
                                with(BottomSheetBottomScopeImpl(this, radius, sheetScope)) {
                                    bottomContent()
                                }
                            }
                        },
                    ) {
                        with(BottomSheetContentScopeImpl(this, sheetScope)) {
                            content()
                        }
                    }
                }
            }
        }
    }
}