package com.adamglin.zithian.example.screens

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.adamglin.zithian.compose.scaffold.ScaffoldScope
import com.adamglin.zithian.compose.scaffold.ScreenScaffold
import com.adamglin.zithian.example.screens.features.components.ComponentsNavKey
import com.adamglin.zithian.example.screens.features.components.ComponentsScreen
import com.adamglin.zithian.example.screens.features.config.ConfigNavKey
import com.adamglin.zithian.example.screens.features.config.ConfigScreen
import com.adamglin.zithian.example.screens.features.theme.ThemeNavKey
import com.adamglin.zithian.example.screens.features.theme.ThemeScreen
import com.adamglin.zithian.example.screens.widgets.AppBottomNavigation
import kotlinx.collections.immutable.persistentSetOf

@Composable
fun MobileApp() {
    val appState = LocalAppState.current
    ScreenScaffold(
        bottom = { Bottom() }
    ) {
        NavDisplay(
            backStack = appState.backstack,
            transitionSpec = {
                slideInHorizontally { it } togetherWith ExitTransition.None
            },
            popTransitionSpec = {
                fadeIn() togetherWith slideOutHorizontally { it }
            },
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator(),
            ),
            onBack = {
                appState.navigate {
                    if (lastOrNull() is TopLevelNavKey)
                        removeLastOrNull()
                }
            },
            entryProvider = entryProvider {
                entry<ThemeNavKey> {
                    ThemeScreen()
                }
                entry<ComponentsNavKey>() {
                    ComponentsScreen()
                }
                entry<ConfigNavKey>() {
                    ConfigScreen()
                }
            }
        )
    }
}

@Composable
fun ScaffoldScope.Bottom() {
    val appState = LocalAppState.current
    val selectedIndex by derivedStateOf {
        TopLevelNavKeys.indexOf(appState.backstack.lastOrNull())
    }
    if (selectedIndex >= 0) {
        AppBottomNavigation(selectedIndex = selectedIndex) {
            val targetNavKey = TopLevelNavKeys.toList()[it]
            appState.navigate {
                if (targetNavKey in appState.backstack) {
                    remove(targetNavKey)
                    add(targetNavKey)
                } else {
                    add(targetNavKey)
                }
            }
        }
    }
}

private val TopLevelNavKeys = persistentSetOf(
    ThemeNavKey,
    ComponentsNavKey,
    ConfigNavKey,
)

fun main() {
    val a = persistentSetOf(1, 2, 3) + persistentSetOf(4, 5, 6, 1)
    println(a)
}