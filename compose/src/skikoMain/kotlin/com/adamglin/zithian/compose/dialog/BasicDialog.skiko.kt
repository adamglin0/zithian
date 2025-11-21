package com.adamglin.zithian.compose.dialog

import androidx.compose.ui.window.PopupProperties

internal actual val basicDialogPopupProperties: PopupProperties
    get() = PopupProperties(
        clippingEnabled = false,
        dismissOnBackPress = true,
        dismissOnClickOutside = true,
        usePlatformInsets = false
    )