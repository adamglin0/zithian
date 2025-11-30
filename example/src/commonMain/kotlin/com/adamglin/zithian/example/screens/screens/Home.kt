package com.adamglin.zithian.example.screens.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adamglin.composecontinuousroundedcornershape.ContinuousRoundedCornerShape
import com.adamglin.zithian.compose.picker.Picker
import com.adamglin.zithian.compose.theme.InteractType
import com.adamglin.zithian.compose.theme.ZithianTheme
import com.adamglin.zithian.compose.utils.shadowBorderWithHover
import kotlinx.collections.immutable.toPersistentList

@Composable
fun Home(
    interactType: InteractType,
    onInteractTypeChange: (InteractType) -> Unit = {},
) {
    Column(
        modifier = Modifier.padding(10.dp)
            .shadowBorderWithHover(ContinuousRoundedCornerShape(20.dp))
            .background(ZithianTheme.colors.surface,ContinuousRoundedCornerShape(20.dp))
            .padding(10.dp)
    ) {
        BasicText(
            text = "Interact type"
        )
        Picker(
            selected = interactType,
            candidates = InteractType.entries.toPersistentList(),
            labelFor = { it.name },
            onValueChange = { onInteractTypeChange(it) }
        )
    }
}