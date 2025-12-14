package com.adamglin.zithian.compose.dialog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import com.adamglin.composecontinuousroundedcornershape.ContinuousRoundedCornerShape
import com.adamglin.zithian.compose.theme.ZithianTheme
import com.adamglin.zithian.compose.utils.Device
import com.adamglin.zithian.compose.utils.LocalWindowRoundedCornerSize
import com.adamglin.zithian.compose.utils.ifNotNull
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import io.github.fletchmckee.liquid.LiquidState
import io.github.fletchmckee.liquid.liquid

interface BasicDialogScope

internal expect val basicDialogPopupProperties: PopupProperties

@Composable
fun BasicDialog(
    isVisible: Boolean,
    backgroundColor: Color = ZithianTheme.colors.surfacePure.copy(.8f),
    liquidState: LiquidState? = null,
    content: @Composable BasicDialogScope.() -> Unit,
) {
    Popup(
        onDismissRequest = { },
        properties = basicDialogPopupProperties,
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
        WindowInsets.systemBars.asPaddingValues().let {
            it.calculateTopPadding() + it.calculateBottomPadding()
        }
        val screenSize = Device.windowSize
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            Box(
                modifier = Modifier.size(screenSize)
                    .background(ZithianTheme.colors.text1.copy(0.4f))
            )
        }
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn() + scaleIn(
                initialScale = 1.5f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessMedium,
                )
            ),
            exit = fadeOut(tween(durationMillis = 100))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsPadding()
                    .padding(DialogPadding)
            ) {
                val shape = ContinuousRoundedCornerShape(LocalWindowRoundedCornerSize.current - DialogPadding / 2)
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .clip(shape)
                        .background(backgroundColor, shape)
                        .align(Alignment.Center)
                        .ifNotNull(liquidState) {
                            Modifier.liquid(it) {
                                this.shape = shape
                                this.curve = 0.2f
                                this.frost = 3.dp
                            }
                        }
                ) {
                    val hazeState = rememberHazeState()
                    Box(modifier = Modifier.hazeSource(hazeState)) {
                        val scope = object : BasicDialogScope {}
                        content(scope)
                    }
                }
            }
        }
    }
}

private val DialogPadding = 30.dp