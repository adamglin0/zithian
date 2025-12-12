package com.adamglin.zithian.example.screens.features.config

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adamglin.zithian.compose.button.PrimaryButton
import com.adamglin.zithian.compose.layout.BasicFiller
import com.adamglin.zithian.compose.scaffold.ScreenScaffold
import com.adamglin.zithian.compose.text.Text
import com.adamglin.zithian.compose.theme.ZithianTheme

@Composable
fun ConfigScreen() {
    ScreenScaffold(
        header = {
            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .statusBarsPadding()
            ) {
                Text("Config", style = ZithianTheme.typography.titleLarge)
            }
        }
    ) {
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            BasicFiller(height = headerHeight)
            PrimaryButton(
                onClick = {},
            ) { Text("Appearance") }
        }
    }
}