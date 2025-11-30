package com.adamglin.zithian.compose.utils

import androidx.compose.ui.input.pointer.PointerIcon


internal actual val PointerIcon.Disabled: PointerIcon
    get() {
        val disabledCursor = org.jetbrains.skiko.Cursor.getSystemCustomCursor("Invalid.16x16")
        return disabledCursor?.let { PointerIcon(it) } ?: PointerIcon.Default
    }