package com.adamglin.zithian.emulator

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.*
import androidx.compose.ui.unit.dp
import com.adamglin.composecontinuousroundedcornershape.ContinuousRoundedCornerShape
import com.adamglin.zithian.compose.scaffold.LocalSheetContainerRadius
import kotlinx.coroutines.awaitCancellation

@OptIn(ExperimentalComposeUiApi::class, InternalComposeUiApi::class)
@Composable
internal fun SystemKeyboardWrapper(
    content: @Composable () -> Unit,
) {
    val platformWindowInsets = LocalPlatformWindowInsets.current
    var isImeVisible by remember { mutableStateOf(false) }
    val density = LocalDensity.current
    val animationImeHeight by animateDpAsState(
        if (isImeVisible) 360.dp else 0.dp
    )

    InterceptPlatformTextInput(
        interceptor = { request, nextHandler ->
            try {
                isImeVisible = true
                awaitCancellation()
            } finally {
                isImeVisible = false
            }
        }
    ) {
        val imePlatformWindowInsets = object : PlatformWindowInsets by platformWindowInsets {
            override val ime: PlatformInsets
                get() = PlatformInsets(bottom = with(density) { animationImeHeight.roundToPx() })
        }
        Box {
            CompositionLocalProvider(
                LocalPlatformWindowInsets provides imePlatformWindowInsets
            ) {
                Box(modifier = Modifier.overridePlatformWindowInsets(imePlatformWindowInsets)) {
                    content()
                }
                AnimatedVisibility(
                    visible = isImeVisible,
                    modifier = Modifier.align(Alignment.BottomCenter),
                    enter = slideInVertically(initialOffsetY = { it }),
                    exit = slideOutVertically(targetOffsetY = { it })
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .height(animationImeHeight)
                            .background(
                                Color(0xFFDDDEE3),
                                ContinuousRoundedCornerShape(LocalSheetContainerRadius.current)
                            )
                    )
                }
            }
        }
    }
}