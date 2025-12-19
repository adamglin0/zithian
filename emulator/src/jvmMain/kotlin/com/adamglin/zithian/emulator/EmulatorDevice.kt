@file:OptIn(InternalComposeUiApi::class)

package com.adamglin.zithian.emulator

import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalPlatformWindowInsets
import androidx.compose.ui.unit.Density
import com.adamglin.composecontinuousroundedcornershape.ContinuousRoundedCornerShape
import com.adamglin.zithian.compose.utils.LocalWindowCornerSize
import com.adamglin.zithian.compose.utils.inverseClip

@Composable
fun EmulatorDevice(
    device: DeviceSpec,
    density: Density,
    content: @Composable () -> Unit,
) {
    val currentContent by rememberUpdatedState(content)
    val currentDevice by rememberUpdatedState(device)
    val currentDensity by rememberUpdatedState(density)

    SwingPanel(
        modifier = Modifier
            .size(device.width, device.height),
        factory = {
            ComposePanel().apply {
                setContent {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black)
                    ) {
                        CompositionLocalProvider(
                            LocalPlatformWindowInsets provides currentDevice,
                            LocalDensity provides currentDensity,
                            LocalWindowCornerSize provides currentDevice.roundedCornerSize
                        ) {
                            currentContent()
                        }
                    }
                }
            }
        },
    )
    Box(
        modifier = Modifier
            .inverseClip(ContinuousRoundedCornerShape(device.roundedCornerSize))
            .background(Color.LightGray)
            .size(device.width, device.height)
    )
    CompositionLocalProvider(LocalDensity provides density) {
        device.cutoutPath?.let { path ->
            Canvas(modifier = Modifier) {
                drawPath(
                    path = path,
                    color = Color.Black
                )
            }
        }
    }
}