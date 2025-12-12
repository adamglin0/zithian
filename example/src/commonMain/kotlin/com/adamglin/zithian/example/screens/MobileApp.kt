package com.adamglin.zithian.example.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.adamglin.zithian.compose.scaffold.ScreenScaffold
import com.adamglin.zithian.example.screens.widgets.AppBottomNavigation

@Composable
fun MobileApp() {
    var selectedIndex by remember { mutableStateOf(0) }

    ScreenScaffold(
        bottom = { AppBottomNavigation(selectedIndex = selectedIndex) { selectedIndex = it } },
    ) {
        Column {
            Box(modifier = Modifier.weight(1f))
        }
    }
}