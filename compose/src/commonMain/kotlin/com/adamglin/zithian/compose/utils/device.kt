package com.adamglin.zithian.compose.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize

object Device {
    val windowRoundedCornerSize: Dp by lazy { getDeviceRoundedCornerSize() }
    val windowSize: DpSize @Composable get() = getWindowSize() ?: DpSize.Unspecified
}

internal expect fun getDeviceRoundedCornerSize(): Dp

@Composable
internal expect fun getWindowSize(): DpSize?