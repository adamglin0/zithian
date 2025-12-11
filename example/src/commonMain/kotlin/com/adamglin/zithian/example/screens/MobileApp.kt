package com.adamglin.zithian.example.screens

import androidx.compose.runtime.Composable
import com.adamglin.zithian.compose.navigation.BottomNavigation
import com.adamglin.zithian.compose.scaffold.ScreenScaffold

@Composable
fun MobileApp() {
    ScreenScaffold {
        BottomNavigation()
    }
}