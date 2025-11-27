package com.adamglin.zithian.example.components

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import com.adamglin.zithian.compose.PrimaryStyledButton
import com.adamglin.zithian.compose.Text

@Composable
fun Buttons() {
    Row {
        PrimaryStyledButton(
            onClick = {},
        ) { Text("Cancel") }
    }
}