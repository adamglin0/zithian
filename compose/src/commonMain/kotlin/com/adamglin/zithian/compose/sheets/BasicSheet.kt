package com.adamglin.zithian.compose.sheets

import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import com.adamglin.composecontinuousroundedcornershape.ContinuousRoundedCornerShape
import com.adamglin.zithian.compose.scaffold.LocalSheetContainerRadius
import com.adamglin.zithian.compose.theme.ZithianTheme
import com.adamglin.zithian.compose.utils.Device
import com.adamglin.zithian.compose.utils.LocalWindowRoundedCornerSize
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState

internal expect val basicSheetPopupProperties: PopupProperties

/**
 * Scope for BasicSheet content that provides access to the container radius.
 */
@Stable
interface BasicSheetContentScope {
    /**
     * The corner radius of the sheet container.
     */
    val containerRadius: Dp
}

internal class BasicSheetContentScopeImpl(
    override val containerRadius: Dp
) : BasicSheetContentScope

@Composable
internal fun BasicSheet(
    isVisible: Boolean,
    onDismissRequest: () -> Unit,
    backgroundColor: Color = ZithianTheme.colors.surface,
    content: @Composable BasicSheetContentScope.() -> Unit,
) {
    val radius = LocalWindowRoundedCornerSize.current - SheetInScreenPadding
    val visibleState = remember { MutableTransitionState(isVisible) }
    visibleState.targetState = isVisible

    if (visibleState.currentState || visibleState.targetState) {
        val screenSize = Device.windowSize
        Popup(
            onDismissRequest = { onDismissRequest() },
            properties = basicSheetPopupProperties,
            popupPositionProvider = object : PopupPositionProvider {
                override fun calculatePosition(
                    anchorBounds: IntRect,
                    windowSize: IntSize,
                    layoutDirection: LayoutDirection,
                    popupContentSize: IntSize
                ): IntOffset {
                    return IntOffset.Zero
                }
            }
        ) {
            AnimatedVisibility(
                visibleState = visibleState,
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                Box(
                    modifier = Modifier.size(screenSize)
                        .clickable(
                            interactionSource = null,
                            indication = null,
                            onClick = { onDismissRequest() }
                        )
                        .background(ZithianTheme.colors.text1.copy(0.4f))
                )
            }
            AnimatedVisibility(
                visibleState = visibleState,
                enter = slideInVertically(animationSpec = spring(stiffness = Spring.StiffnessHigh)) { it },
                exit = slideOutVertically { it }
            ) {
                Box(
                    modifier = Modifier
                        .size(screenSize)
                        .padding(horizontal = SheetInScreenPadding)
                        .navigationBarsPadding()
                ) {
                    val shape = ContinuousRoundedCornerShape(radius)
                    val hazeState = rememberHazeState()
                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .clip(shape)
                            .background(backgroundColor, shape)
                            .align(Alignment.BottomCenter)
                            .hazeSource(hazeState)
                            .clickable(
                                interactionSource = null,
                                indication = null,
                                onClick = {}
                            )
                    ) {
                        AnimatedContent(Unit) {
                            Box(
                                modifier = Modifier.animateContentSize()
                            ) {
                                CompositionLocalProvider(LocalSheetContainerRadius provides radius) {
                                    with(BasicSheetContentScopeImpl(radius)) {
                                        content()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * A bottom sheet component that provides a simple container for sheet content.
 *
 * For structured layouts with header/bottom areas, use [SheetScaffold] inside the content lambda.
 * The [containerRadius] is available through [BasicSheetContentScope] and [LocalSheetContainerRadius]
 * for radius-aware components.
 *
 * @param isVisible Controls the visibility of the sheet with enter/exit animations.
 * @param onDismissRequest Callback invoked when the sheet should be dismissed.
 * @param backgroundColor Background color of the sheet container.
 * @param content The main content of the sheet. Has access to [BasicSheetContentScope.containerRadius].
 */
@Composable
fun BottomSheet(
    isVisible: Boolean,
    onDismissRequest: () -> Unit,
    backgroundColor: Color = ZithianTheme.colors.surface,
    content: @Composable BasicSheetContentScope.() -> Unit,
) {
    BasicSheet(
        isVisible = isVisible,
        onDismissRequest = onDismissRequest,
        backgroundColor = backgroundColor,
        content = content,
    )
}

private val SheetInScreenPadding = 13.dp
