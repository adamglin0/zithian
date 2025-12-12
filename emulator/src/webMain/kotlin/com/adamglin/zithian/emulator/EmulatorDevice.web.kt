package com.adamglin.zithian.emulator

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.InternalComposeUiApi
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalPlatformWindowInsets

@OptIn(InternalComposeUiApi::class)
@androidx.compose.runtime.Composable
actual fun EmulatorDevice(
    device: Device,
    onDeviceChange: (Device) -> Unit,
    content: @androidx.compose.runtime.Composable (() -> Unit)
) {
    val density = device.density
    CompositionLocalProvider(
        LocalPlatformWindowInsets provides device,
        LocalDensity provides density
    ) {
        content()
    }
}