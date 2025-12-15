package com.adamglin.zithian.example.screens.features.components

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.adamglin.zithian.compose.layout.BasicFiller
import com.adamglin.zithian.compose.layout.FadingEdge
import com.adamglin.zithian.compose.scaffold.ScreenScaffold
import com.adamglin.zithian.example.screens.features.components.widgets.Buttons
import com.adamglin.zithian.example.screens.features.components.widgets.Pickers
import com.adamglin.zithian.example.screens.widgets.SimpleTextTopBar
import com.adamglin.zithian.example.screens.widgets.TopLevelSharableBottomNavigation

@Composable
fun ComponentsScreen() {
    ScreenScaffold(
        header = {
            SimpleTextTopBar("Components")
        },
        bottom = { TopLevelSharableBottomNavigation() },
        backgroundColor = Color.White
    ) {
        val horizontalScrollState = rememberScrollState()
        FadingEdge(
            horizontalScrollState,
            orientation = Orientation.Horizontal
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .horizontalScroll(horizontalScrollState)
            ) {
                BasicFiller(height = headerHeight)
                Column(modifier = Modifier.padding(20.dp, 20.dp)) {
                    Buttons()
                    Pickers()
                }
            }
        }
    }
}