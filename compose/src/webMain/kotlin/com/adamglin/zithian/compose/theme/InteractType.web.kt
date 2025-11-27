package com.adamglin.zithian.compose.theme

import kotlin.js.ExperimentalWasmJsInterop

actual val InteractType.Companion.platformDefault: InteractType
    get() = if (jsHasMouseLikePointer()) InteractType.Pointer else InteractType.Touch

@OptIn(ExperimentalWasmJsInterop::class)
@JsFun("() => matchMedia('(any-hover: hover), (any-pointer: fine)').matches")
external fun jsHasMouseLikePointer(): Boolean