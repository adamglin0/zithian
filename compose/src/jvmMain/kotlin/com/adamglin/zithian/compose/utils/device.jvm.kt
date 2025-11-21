package com.adamglin.zithian.compose.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

internal actual fun getDeviceRoundedCornerSize(): Dp {
    return 48.dp
}

@Composable
internal actual fun getWindowSize(): DpSize? {
    val density = LocalDensity.current
    val containerSizePx = LocalWindowInfo.current.containerSize
    return with(density) {
        DpSize(
            containerSizePx.width.toDp(),
            containerSizePx.height.toDp()
        )
    }
}