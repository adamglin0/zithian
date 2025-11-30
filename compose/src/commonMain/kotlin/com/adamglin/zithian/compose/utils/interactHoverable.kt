package com.adamglin.zithian.compose.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import com.adamglin.zithian.compose.theme.InteractType

fun Modifier.interactPointer(
    interactType: InteractType,
): Modifier = this.then(
    other = if (interactType == InteractType.Pointer)
        Modifier.pointerHoverIcon(PointerIcon.Hand)
    else Modifier
)