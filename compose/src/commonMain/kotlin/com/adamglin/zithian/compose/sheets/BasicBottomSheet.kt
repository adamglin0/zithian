package com.adamglin.zithian.compose.sheets

import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import com.adamglin.zithian.compose.theme.ZithianTheme
import com.adamglin.zithian.compose.utils.Device

/**
 * Basic bottom sheet that handles animations, scrim overlay, and size constraints.
 *
 * This is the foundational sheet component. Use [BottomSheet] for the full-featured
 * variant with positioning, padding, shape, and header/bottom slots.
 *
 * The sheet appears with enter animation when added to composition and dismisses
 * with exit animation when [BottomSheetScope.dismiss] is called from within [content].
 *
 * **Responsibilities:**
 * - Enter/exit animations (scrim fade, content slide)
 * - Scrim overlay (dismissable on click)
 * - Size constraints (full screen container)
 *
 * **Animation behavior:**
 * - Scrim: fadeIn/fadeOut
 * - Content: slideInVertically with spring / slideOutVertically
 *
 * @param onDismissRequest Callback invoked after the exit animation completes.
 *                         Use this to remove the sheet from composition.
 * @param modifier Modifier to be applied to the content container.
 * @param properties Properties for configuring the popup behavior.
 * @param scrimColor The color of the scrim overlay behind the sheet.
 * @param content The content to display inside the sheet. Use [BottomSheetScope.dismiss]
 *                to trigger the exit animation.
 */
@Composable
fun BasicBottomSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    properties: BottomSheetProperties = BottomSheetProperties(),
    scrimColor: Color = ZithianTheme.colors.text1.copy(alpha = 0.4f),
    content: @Composable BoxScope.(BottomSheetScope) -> Unit,
) {
    val state = rememberBasicBottomSheetState(onDismissRequest)
    val scope = BottomSheetScopeImpl(state)

    if (state.transitionState.currentState || state.transitionState.targetState) {
        val screenSize = Device.windowSize
        Popup(
            onDismissRequest = state::dismiss,
            properties = properties.toPopupProperties(),
            popupPositionProvider = object : PopupPositionProvider {
                override fun calculatePosition(
                    anchorBounds: IntRect,
                    windowSize: IntSize,
                    layoutDirection: LayoutDirection,
                    popupContentSize: IntSize
                ): IntOffset = IntOffset.Zero
            }
        ) {
            // Scrim layer with fade animation
            AnimatedVisibility(
                visibleState = state.transitionState,
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                Box(
                    modifier = Modifier
                        .size(screenSize)
                        .clickable(
                            interactionSource = null,
                            indication = null,
                            onClick = state::dismiss
                        )
                        .background(scrimColor)
                )
            }

            // Content layer with slide animation
            AnimatedVisibility(
                visibleState = state.transitionState,
                enter = slideInVertically(
                    animationSpec = spring(stiffness = Spring.StiffnessHigh)
                ) { it },
                exit = slideOutVertically { it }
            ) {
                Box(
                    modifier = modifier.size(screenSize),
                    contentAlignment = Alignment.BottomCenter,
                ) {
                    // Inner container for content
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                interactionSource = null,
                                indication = null,
                                onClick = {} // Consume clicks to prevent dismissal
                            )
                    ) {
                        content(scope)
                    }
                }
            }
        }
    }
}

