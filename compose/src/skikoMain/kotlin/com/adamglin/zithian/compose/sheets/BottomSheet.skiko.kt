package com.adamglin.zithian.compose.sheets

import androidx.compose.ui.window.PopupProperties

internal actual fun BottomSheetProperties.toPopupProperties(): PopupProperties {
    return PopupProperties(
        focusable = focusable,
        clippingEnabled = clippingEnabled,
        dismissOnBackPress = dismissOnBackPress,
        dismissOnClickOutside = dismissOnClickOutside,
        usePlatformInsets = false
    )
}