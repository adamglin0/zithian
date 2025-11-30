package com.adamglin.zithian.compose.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import com.adamglin.zithian.compose.theme.InteractType

internal expect val PointerIcon.Disabled: PointerIcon

fun Modifier.interactPointer(
    interactType: InteractType,
    enabled: Boolean,
): Modifier = this.then(
    other = if (interactType == InteractType.Pointer)
        Modifier.pointerHoverIcon(
            if (enabled) PointerIcon.Hand else PointerIcon.Default
        )
    else Modifier
)