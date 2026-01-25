package com.adamglin.zithian.example

import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.application
import com.adamglin.zithian.compose.theme.InteractType
import com.adamglin.zithian.compose.theme.ZithianTheme
import com.adamglin.zithian.emulator.EmulatorWindow
import com.adamglin.zithian.emulator.config.iphone16
import com.adamglin.zithian.emulator.rememberEmulatorWindowState
import com.adamglin.zithian.example.screens.AppState
import com.adamglin.zithian.example.screens.ExampleAppFontFamilyProvider
import com.adamglin.zithian.example.screens.LocalAppState
import com.adamglin.zithian.example.screens.MobileApp
import com.adamglin.zithian.example.screens.features.theme.ThemeNavKey

internal fun touchApplication() = application {
    val state = rememberEmulatorWindowState(
        deviceSpec = iphone16,
        isAlwaysOnTop = true,
    )
    EmulatorWindow(state = state) {
        val appState = AppState(listOf(ThemeNavKey))
        CompositionLocalProvider(
            LocalAppState provides appState
        ) {
            ExampleAppFontFamilyProvider {
                ZithianTheme(
                    interactType = InteractType.Touch
                ) {
                    SharedTransitionLayout {
                        appState.sharedTransitionScope = this
                        MobileApp()
                    }
                }
            }
        }
    }
}