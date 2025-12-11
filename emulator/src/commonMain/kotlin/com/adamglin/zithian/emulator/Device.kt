package com.adamglin.zithian.emulator

import androidx.compose.runtime.Immutable
import androidx.compose.ui.InternalComposeUiApi
import androidx.compose.ui.platform.PlatformWindowInsets
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp

interface DeviceAppearance {
    val width: Dp
    val height: Dp
    val roundedCornerSize: Dp
}

@Immutable
@OptIn(InternalComposeUiApi::class)
interface Device : DeviceAppearance, PlatformWindowInsets {
    val density: Density

    companion object {}
}