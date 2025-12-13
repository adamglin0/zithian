@file:OptIn(InternalComposeUiApi::class)

package com.adamglin.zithian.emulator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.InternalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposePanel
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalPlatformWindowInsets
import androidx.compose.ui.unit.Density
import com.adamglin.composecontinuousroundedcornershape.ContinuousRoundedCornerShape

@Composable
fun EmulatorDevice(
    device: DeviceSpec,
    density: Density,
    content: @Composable () -> Unit,
) {
    val currentContent by rememberUpdatedState(content)
    val currentDevice by rememberUpdatedState(device)
    val currentDensity by rememberUpdatedState(density)

    Box(
        modifier = Modifier
            .size(device.width, device.height)
    ) {
        SwingPanel(
            modifier = Modifier.fillMaxSize(),
            factory = {
                ComposePanel().apply {
                    setContent {
                        // 使用 rememberUpdatedState 的值，这样可以响应外部变化
                        val shape = ContinuousRoundedCornerShape(currentDevice.roundedCornerSize)
                        Box(
                            modifier = Modifier
                                .fillMaxSize() // 改为 fillMaxSize，大小由外层控制
                                .clip(shape)
                                .background(Color.White, shape)
                        ) {
                            CompositionLocalProvider(
                                LocalPlatformWindowInsets provides currentDevice,
                                LocalDensity provides currentDensity
                            ) {
                                currentContent()
                            }
                        }
                    }
                }
            },
            update = { panel ->
                // 如果需要在某些条件下强制更新，可以在这里处理
                // 但由于使用了 rememberUpdatedState，通常不需要
            }
        )
    }
//    Box(
//        modifier = Modifier
//            .background(Color.Red)
//    ) {
//        CompositionLocalProvider(LocalDensity provides density) {
//            device.cutoutPath?.let { path ->
//                Canvas(modifier = Modifier) {
//                    drawPath(
//                        path = path,
//                        color = Color.Black
//                    )
//                }
//            }
//        }
//    }
}