package com.adamglin.zithian.example.screens

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.adamglin.zithian.example.screens.features.components.ComponentsNavKey
import com.adamglin.zithian.example.screens.features.components.ComponentsScreen
import com.adamglin.zithian.example.screens.features.config.ConfigNavKey
import com.adamglin.zithian.example.screens.features.config.ConfigScreen
import com.adamglin.zithian.example.screens.features.liquid_test.LiquidTestNavKey
import com.adamglin.zithian.example.screens.features.liquid_test.LiquidTestScreen
import com.adamglin.zithian.example.screens.features.theme.ThemeNavKey
import com.adamglin.zithian.example.screens.features.theme.ThemeScreen
import com.adamglin.zithian.navigation3.decorator.rememberScreenCommonHeaderNavEntryDecorator

@Composable
fun MobileApp() {
    val appState = LocalAppState.current
    NavDisplay(
        backStack = appState.backstack,
        transitionSpec = {
            if (targetState.metadata["type"] == TopLevelNavKey::class) {
                EnterTransition.None togetherWith ExitTransition.None
            } else slideInHorizontally { it } togetherWith ExitTransition.None
        },
        popTransitionSpec = {
            fadeIn() togetherWith slideOutHorizontally { it }
        },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
            rememberScreenCommonHeaderNavEntryDecorator(),
        ),
        onBack = {
            appState.navigate {
                if (lastOrNull() is TopLevelNavKey)
                    removeLastOrNull()
            }
        },
        entryProvider = entryProvider {
            entry<ThemeNavKey>(metadata = mapOf("type" to TopLevelNavKey::class)) {
                ThemeScreen()
            }
            entry<ComponentsNavKey>(metadata = mapOf("type" to TopLevelNavKey::class)) {
                ComponentsScreen()
            }
            entry<ConfigNavKey>(metadata = mapOf("type" to TopLevelNavKey::class)) {
                ConfigScreen()
            }
            entry<LiquidTestNavKey> {
                LiquidTestScreen()
            }
        }
    )
}