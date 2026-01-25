package com.adamglin.zithian.emulator.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBar
import com.adamglin.zithian.compose.generated.resources.ZithianResources
import com.adamglin.zithian.compose.generated.resources.ic_check
import com.adamglin.zithian.emulator.EmulatorWindowState
import com.adamglin.zithian.generated.registeredDevices
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun FrameWindowScope.EmulatorMenuBar(
    state: EmulatorWindowState,
) {
    val checkIconPainter = painterResource(ZithianResources.drawable.ic_check)
    MenuBar {
        Menu(text = "Devices") {
            registeredDevices.fastForEach { device ->
                Item(
                    text = device.name,
                    enabled = state.deviceSpec != device,
                    onClick = { state.deviceSpec = device }
                )
            }
        }
        Menu(text = "Config") {
            Item(
                text = System.getProperty("java.home").toString(),
                onClick = {},
            )
            Item(
                text = "Always on Top",
                icon = if (state.isAlwaysOnTop) checkIconPainter else null,
                onClick = { state.isAlwaysOnTop = !state.isAlwaysOnTop },
            )
        }
    }
}