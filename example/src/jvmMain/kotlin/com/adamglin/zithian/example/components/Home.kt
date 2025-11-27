package com.adamglin.zithian.example.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import com.adamglin.zithian.compose.theme.LocalInteractType

@Composable
fun Home() {
    val interactType = LocalInteractType.current
    Column {
        BasicText(
            text = "Interact type: $interactType"
        )
    }
}