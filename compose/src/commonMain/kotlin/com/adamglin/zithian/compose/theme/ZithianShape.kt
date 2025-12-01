package com.adamglin.zithian.compose.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class ZithianShapes(
    val extraSmall: Dp = 2.dp,
    val small: Dp = 5.dp,
    val medium: Dp = 10.dp,
    val large: Dp = 15.dp,
    val extraLarge: Dp = 20.dp,
)

val LocalZithianShapes = staticCompositionLocalOf { ZithianShapes() }

