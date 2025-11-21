package com.adamglin.zithian.compose.utils

import androidx.compose.ui.Modifier

inline fun <T> Modifier.ifNotNull(
    value: T?,
    block: (T) -> Modifier
): Modifier =
    if (value != null) this.then(block(value)) else this