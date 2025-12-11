package com.adamglin.zithian.example

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application


internal fun pointerApplication() = application {
    Window(onCloseRequest = ::exitApplication) {

    }
}