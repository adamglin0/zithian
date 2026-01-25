package com.adamglin.zithian.emulator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.retain.retain
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import com.adamglin.zithian.emulator.ui.EmulatorMenuBar
import com.jetbrains.JBR
import kotlinx.coroutines.Dispatchers

@Composable
fun EmulatorWindow(
    state: EmulatorWindowState,
    content: @Composable () -> Unit,
) {
    System.setProperty("compose.interop.blending", "true")
    System.setProperty("compose.swing.render.on.graphics", "true")

    val windowSize by retain(state.deviceSpec) {
        mutableStateOf(
            DpSize(
                width = state.deviceSpec.width + WrapperPaddingValuesStart + WrapperPaddingValuesEnd,
                height = state.deviceSpec.height + WrapperPaddingValuesTop + WrapperPaddingValuesBottom
            )
        )
    }

    Window(
        onCloseRequest = {},
        state = WindowState(size = windowSize),
    ) {
        EmulatorMenuBar(state = state)
        LaunchedEffect(Unit) {
            runCatching {
                with(Dispatchers.Main.immediate) {
                    JBR.getRoundedCornersManager().setRoundedCorners(window, 10f)
                    JBR.getWindowDecorations().setCustomTitleBar(
                        window,
                        JBR.getWindowDecorations().createCustomTitleBar().apply { height = 32f }
                    )
                }
            }
        }
        WindowDraggableArea {
            Box(modifier = Modifier.fillMaxSize().background(Color.LightGray))
        }
        Box(modifier = Modifier.padding(20.dp)) {
            EmulatorDevice(device = state.deviceSpec) {
                content()
            }
        }
    }
}

private val WrapperPaddingValuesStart = 10.dp
private val WrapperPaddingValuesEnd = 10.dp
private val WrapperPaddingValuesTop = 32.dp
private val WrapperPaddingValuesBottom = 10.dp
