package com.adamglin.zithian.compose.annotation

import com.adamglin.zithian.compose.theme.InteractType

@Target(AnnotationTarget.FUNCTION)
annotation class InteractTypeOnly(
    val type: InteractType
)
