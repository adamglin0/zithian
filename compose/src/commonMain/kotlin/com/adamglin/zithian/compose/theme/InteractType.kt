package com.adamglin.zithian.compose.theme

import androidx.compose.runtime.staticCompositionLocalOf

val LocalInteractType = staticCompositionLocalOf<InteractType> { error("InteractType not provided!") }

enum class InteractType {
    Pointer,
    Touch;

    companion object
}

expect val InteractType.Companion.platformDefault: InteractType