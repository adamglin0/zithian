package com.adamglin.zithian.emulator

import androidx.compose.runtime.Composable

@Composable
expect fun EmulatorDevice(
    device: Device,
    content: @Composable () -> Unit,
)