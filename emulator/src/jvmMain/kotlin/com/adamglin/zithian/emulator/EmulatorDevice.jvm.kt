package com.adamglin.zithian.emulator

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.retain.retain
import androidx.compose.ui.InternalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalPlatformWindowInsets
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import com.adamglin.composecontinuousroundedcornershape.ContinuousRoundedCornerShape
import com.adamglin.zithian.compose.theme.ZithianTheme
import com.jetbrains.JBR
import kotlinx.coroutines.Dispatchers

private val WrapperPaddingValuesStart = 10.dp
private val WrapperPaddingValuesEnd = 10.dp
private val WrapperPaddingValuesTop = 32.dp
private val WrapperPaddingValuesBottom = 10.dp


@OptIn(InternalComposeUiApi::class)
@Composable
actual fun EmulatorDevice(
    device: Device,
    onDeviceChange: (Device) -> Unit,
    content: @Composable () -> Unit,
) {
    val density = device.density
    val windowState = retain(device) {
        WindowState(
            size = DpSize(
                width = device.width + WrapperPaddingValuesStart + WrapperPaddingValuesEnd,
                height = device.height + WrapperPaddingValuesTop + WrapperPaddingValuesBottom
            )
        )
    }
    Window(
        state = windowState,
        undecorated = false,
        resizable = false,
        onCloseRequest = {},
    ) {
        LaunchedEffect(Unit) {
            with(Dispatchers.Main.immediate) {
                JBR.getRoundedCornersManager().setRoundedCorners(window, 10f)
                JBR.getWindowDecorations().setCustomTitleBar(
                    window,
                    JBR.getWindowDecorations().createCustomTitleBar().apply { height = 32f }
                )
            }
        }
        WindowDraggableArea {
            ZithianTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.LightGray)
                ) {
                    EmulatorToolBar(
                        selectedDevice = device,
                        onDeviceChange = onDeviceChange
                    )
                }
            }
        }
        val shape = ContinuousRoundedCornerShape(device.roundedCornerSize)
        Box(
            modifier = Modifier
                .offset(x = WrapperPaddingValuesStart, y = WrapperPaddingValuesTop)
                .size(device.width, device.height)
                .clip(shape)
                .background(Color.White, shape)
        ) {
            CompositionLocalProvider(
                LocalPlatformWindowInsets provides device,
                LocalDensity provides density
            ) {
                content()
            }
        }

        Box(
            modifier = Modifier
                .offset(x = WrapperPaddingValuesStart, y = WrapperPaddingValuesTop)
                .background(Color.Red)
        ) {
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
    }
}