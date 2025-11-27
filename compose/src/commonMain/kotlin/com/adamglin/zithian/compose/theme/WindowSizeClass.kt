package com.adamglin.zithian.compose.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class WindowSizeClass {
    /** < 600.dp */
    Compact,

    /** 600.dp - 840.dp */
    Medium,

    /** ≥ 840.dp */
    Expanded;

    companion object {
        fun fromWidth(width: Dp) {
            when(width) {
                in 0.dp..599.dp -> Compact
                in 600.dp..839.dp -> Medium
                else -> Expanded
            }
        }
    }
}