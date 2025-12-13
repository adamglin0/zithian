package com.adamglin.zithian.emulator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.runtime.*
import androidx.compose.runtime.retain.retain
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import com.adamglin.composecontinuousroundedcornershape.ContinuousRoundedCornerShape
import com.adamglin.zithian.compose.theme.ZithianTheme
import com.jetbrains.JBR
import kotlinx.coroutines.Dispatchers

@Composable
fun ApplicationScope.EmulatorTable(
    initialDevice: DeviceSpec,
    content: @Composable () -> Unit,
) {
    var device by retain(initialDevice) {
        mutableStateOf(initialDevice)
    }

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
        onCloseRequest = ::exitApplication,
        undecorated = true,
        resizable = false,
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
                        onDeviceChange = { device = it }
                    )
                }
            }
        }
        val position = WindowPosition(
            x = windowState.position.x + WrapperPaddingValuesStart,
            y = windowState.position.y + WrapperPaddingValuesTop
        )
        if (!(position.x.value.isNaN() || position.y.value.isNaN())) {
            Box(
                modifier = Modifier
                    .clip(ContinuousRoundedCornerShape(device.roundedCornerSize))
                    .offset(x = WrapperPaddingValuesStart, y = WrapperPaddingValuesTop)
                    .background(Color.Red)
            ) {
                EmulatorDevice(
                    device = device,
                    density = device.density,
                ) {
                    content()
                }
            }
        }

    }
}

private val WrapperPaddingValuesStart = 10.dp
private val WrapperPaddingValuesEnd = 10.dp
private val WrapperPaddingValuesTop = 32.dp
private val WrapperPaddingValuesBottom = 10.dp