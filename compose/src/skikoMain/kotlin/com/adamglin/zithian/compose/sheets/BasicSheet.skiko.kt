package com.adamglin.zithian.compose.sheets

import androidx.compose.ui.window.PopupProperties

internal actual val basicSheetPopupProperties: PopupProperties
    get() = PopupProperties(
        clippingEnabled = false,
        dismissOnBackPress = true,
        dismissOnClickOutside = true,
        usePlatformInsets = false
    )