package com.adamglin.zithian.compose.utils

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp

val LocalWindowRoundedCornerSize = staticCompositionLocalOf<Dp> { error("No rounded corner size provided!") }