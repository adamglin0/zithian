package com.adamglin.zithian.example.screens.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import com.adamglin.zithian.compose.scaffold.ScaffoldScope
import com.adamglin.zithian.example.screens.AppState
import com.adamglin.zithian.example.screens.LocalAppState
import com.adamglin.zithian.example.screens.TopLevelNavKey

@Composable
fun ScaffoldScope.TopLevelSharableBottomNavigation() {
    val appState = LocalAppState.current
    val selectedIndex by derivedStateOf {
        val lastTopLevelNavKey = appState.backstack.last { it is TopLevelNavKey }
        AppState.topLevelNavKeys.indexOf(lastTopLevelNavKey)
    }
    val content = movableContentOf { ->
        AppBottomNavigation(
            selectedIndex = selectedIndex
        ) {
            val targetNavKey = AppState.topLevelNavKeys.toList()[it]
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
    content()
}
