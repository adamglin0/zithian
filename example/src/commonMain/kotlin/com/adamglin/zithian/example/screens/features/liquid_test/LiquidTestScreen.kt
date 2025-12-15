package com.adamglin.zithian.example.screens.features.liquid_test

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.adamglin.zithian.compose.layout.BasicFiller
import com.adamglin.zithian.compose.scaffold.ScreenScaffold
import com.adamglin.zithian.example.screens.LocalAppState
import com.adamglin.zithian.example.screens.widgets.SimpleTextTopBar
import zithian.example.generated.resources.Res

@Composable
fun LiquidTestScreen() {
    val appState = LocalAppState.current
    ScreenScaffold(
        header = {
            SimpleTextTopBar(
                text = "LiquidTest",
                onBack = { appState.navigate { removeLastOrNull() } }
            )
        },
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            BasicFiller(height = headerHeight + 10.dp)
            AsyncImage(
                model = Res.getUri("drawable/img_pill.png"),
                contentDescription = null,
                colorFilter = ColorFilter.tint(
                    color = Color.Green.copy(alpha = 0.2f),
                    blendMode = BlendMode.SrcAtop // 或 Modulate / Multiply
                )
            )
        }
    }
}