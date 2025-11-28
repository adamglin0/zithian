package com.adamglin.zithian.example.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import com.adamglin.zithian.compose.picker.Picker
import com.adamglin.zithian.compose.theme.InteractType
import com.adamglin.zithian.compose.theme.LocalInteractType
import kotlinx.collections.immutable.toPersistentList

@Composable
fun Home(
    interactType: InteractType,
    onInteractTypeChange: (InteractType) -> Unit = {},
) {
    Column {
        BasicText(
            text = "Interact type"
        )
        Picker(
            selected = interactType,
            candidates = InteractType.entries.toPersistentList(),
            labelFor = { it.name },
            onValueChange = { onInteractTypeChange(it)}
        )
    }
}