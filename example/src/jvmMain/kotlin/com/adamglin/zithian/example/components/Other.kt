package com.adamglin.zithian.example.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import com.adamglin.zithian.compose.Picker
import kotlinx.collections.immutable.toPersistentList

@Composable
fun Other() {
    Column {
        var mode by remember { mutableStateOf(Mode.Auto) }
        Picker(
            selected = mode,
            candidates = Mode.entries.toPersistentList(),
            labelFor = { it.name },
            onValueChange = { mode = it }
        )
    }
}

private enum class Mode {
    LowPower,
    Auto,
    HighPower,
}