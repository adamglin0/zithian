package com.adamglin.zithian.emulator

import androidx.compose.runtime.Composable

@Composable
expect fun EmulatorDevice(
    device: Device,
    onDeviceChange: (Device) -> Unit,
    content: @Composable () -> Unit,
)