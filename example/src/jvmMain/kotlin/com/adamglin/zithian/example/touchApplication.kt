package com.adamglin.zithian.example

import androidx.compose.ui.unit.Density
import androidx.compose.ui.window.application
import com.adamglin.zithian.compose.theme.InteractType
import com.adamglin.zithian.compose.theme.ZithianTheme
import com.adamglin.zithian.emulator.Device
import com.adamglin.zithian.emulator.EmulatorDevice
import com.adamglin.zithian.emulator.config.iphone17
import com.adamglin.zithian.example.screens.MobileApp

internal fun touchApplication() = application {
    EmulatorDevice(
        device = Device.iphone17(Density(1.1f))
    ) {
        ZithianTheme(
            interactType = InteractType.Touch
        ) {
            MobileApp()
        }
    }
}