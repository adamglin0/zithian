package com.adamglin.zithian.example.screens.features.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.adamglin.zithian.compose.scaffold.ScreenScaffold
import com.adamglin.zithian.compose.text.Text
import com.adamglin.zithian.example.screens.widgets.SimpleTextTopBar
import com.adamglin.zithian.example.screens.widgets.TopLevelSharableBottomNavigation

@Composable
fun ComponentsScreen() {
    ScreenScaffold(
        header = {
            SimpleTextTopBar("Components")
        },
        bottom = { TopLevelSharableBottomNavigation() }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text("ComponentsScreen")
        }
    }
}