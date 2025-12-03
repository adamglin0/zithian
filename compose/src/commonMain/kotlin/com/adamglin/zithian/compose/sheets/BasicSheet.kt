package com.adamglin.zithian.compose.sheets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import com.adamglin.composecontinuousroundedcornershape.ContinuousRoundedCornerShape
import com.adamglin.zithian.compose.scaffold.BasicScaffold
import com.adamglin.zithian.compose.scaffold.ScaffoldScope
import com.adamglin.zithian.compose.theme.ZithianTheme
import com.adamglin.zithian.compose.utils.Device
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import io.github.fletchmckee.liquid.rememberLiquidState

internal expect val basicSheetPopupProperties: PopupProperties

@Composable
internal fun BasicSheet(
    isVisible: Boolean,
    onDismissRequest: () -> Unit,
    backgroundColor: Color = ZithianTheme.colors.surface,
    content: @Composable () -> Unit,
) {
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
        val screenSize = Device.windowSize
        AnimatedVisibility(
            visible = isVisible,
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
            visible = isVisible,
            enter = slideInVertically(animationSpec = spring(stiffness = Spring.StiffnessHigh)) { it },
            exit = slideOutVertically { it }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = SheetInScreenPadding)
                    .navigationBarsPadding()
            ) {
                val shape = ContinuousRoundedCornerShape(Device.windowRoundedCornerSize - SheetInScreenPadding)
                val hazeState = rememberHazeState()
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .clip(shape)
                        .background(backgroundColor, shape)
                        .align(Alignment.BottomCenter)
                        .hazeSource(hazeState)
                ) {
                    content()
                }
            }
        }
    }
}

@Composable
fun BasicSheet(
    isVisible: Boolean,
    onDismissRequest: () -> Unit,
    header: @Composable ScaffoldScope.() -> Unit,
    backgroundColor: Color = ZithianTheme.colors.surface,
    content: @Composable ScaffoldScope.() -> Unit,
) {
    BasicSheet(
        isVisible = isVisible,
        onDismissRequest = onDismissRequest,
        backgroundColor = backgroundColor
    ) {
        BasicScaffold(
            backgroundColor = backgroundColor,
            header = {
                header()
            }
        ) { content() }
    }
}

private val SheetInScreenPadding = 20.dp