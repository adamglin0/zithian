package com.adamglin.zithian.emulator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.runtime.*
import androidx.compose.runtime.retain.retain
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import com.adamglin.zithian.compose.generated.resources.ZithianResources
import com.adamglin.zithian.compose.generated.resources.ic_check
import com.adamglin.zithian.generated.registeredDevices
import com.jetbrains.JBR
import kotlinx.coroutines.Dispatchers
import org.jetbrains.compose.resources.painterResource

@Composable
fun ApplicationScope.EmulatorTable(
    initialDevice: DeviceSpec,
    content: @Composable () -> Unit,
) {
    System.setProperty("compose.interop.blending", "true")
    System.setProperty("compose.swing.render.on.graphics", "true")
    var alwaysOnTop by remember { mutableStateOf(false) }
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
        undecorated = false,
        resizable = false,
        alwaysOnTop = alwaysOnTop,
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

        val checkIconPainter = painterResource(ZithianResources.drawable.ic_check)
        MenuBar {
            Menu(
                text = "Device"
            ) {
                registeredDevices.fastForEach { mDevice ->
                    Item(
                        text = mDevice.name,
                        enabled = device != mDevice,
                        onClick = { device = mDevice }
                    )
                }
            }
            Menu(
                text = "Config"
            ) {
                Item(
                    text = "Always on Top",
                    icon = if (alwaysOnTop) checkIconPainter else null,
                    onClick = { alwaysOnTop = !alwaysOnTop },
                )
            }
        }

        WindowDraggableArea {
            Box(modifier = Modifier.fillMaxSize().background(Color.LightGray))
        }

        Box(
            modifier = Modifier
                .offset(x = WrapperPaddingValuesStart, y = WrapperPaddingValuesTop)
        ) {
            CompositionLocalProvider(
                LocalDensity provides device.density
            ) {
                EmulatorDevice(device = device) {
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