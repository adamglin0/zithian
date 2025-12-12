package com.adamglin.zithian.example.screens.features.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.adamglin.zithian.compose.scaffold.ScreenScaffold
import com.adamglin.zithian.compose.text.Text

@Composable
fun ComponentsScreen() {
    ScreenScaffold {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text("ComponentsScreen")
        }
    }
}