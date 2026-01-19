@file:OptIn(InternalComposeUiApi::class)

package com.adamglin.zithian.emulator

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.InternalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposePanel
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.overridePlatformWindowInsets
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalPlatformWindowInsets
import com.adamglin.composecontinuousroundedcornershape.ContinuousRoundedCornerShape
import com.adamglin.zithian.compose.utils.LocalWindowRoundedCornerSize
import com.adamglin.zithian.compose.utils.inverseClip

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EmulatorDevice(
    device: DeviceSpec,
    content: @Composable () -> Unit,
) {
    val currentContent by rememberUpdatedState(content)
    val currentDevice by rememberUpdatedState(device)

    // Get the actual density and adjust the device spec for the outer content
    val actualDensity = LocalDensity.current
    val adjustedDevice = remember(actualDensity.density) {
        currentDevice.adjustedForDensity(actualDensity)
    }

    SwingPanel(
        modifier = Modifier
            .size(device.width, device.height),
        factory = {
            ComposePanel().apply {
                isClearFocusOnMouseDownEnabled = false
                setContent {
                    // Get the actual density inside the SwingPanel
                    // This may be different from the outer density!
                    val innerDensity = LocalDensity.current
                    val innerAdjustedDevice = remember(innerDensity.density) {
                        currentDevice.adjustedForDensity(innerDensity)
                    }

                    Box(
                        modifier = Modifier
                            .overridePlatformWindowInsets(innerAdjustedDevice)
                            .fillMaxSize()
                            .background(Color.Black)
                    ) {
                        CompositionLocalProvider(
                            LocalPlatformWindowInsets provides innerAdjustedDevice,
                            LocalWindowRoundedCornerSize provides currentDevice.roundedCornerSize
                        ) {
                            SystemKeyboardWrapper {
                                currentContent()
                            }
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
    adjustedDevice.cutoutPath?.let { path ->
        Canvas(modifier = Modifier) {
            drawPath(
                path = path,
                color = Color.Black
            )
        }
    }
}