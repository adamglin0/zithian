package com.adamglin.zithian.example.screens.features.components.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adamglin.zithian.compose.picker.WheelPicker
import com.adamglin.zithian.compose.picker.WheelPickerEffect
import com.adamglin.zithian.compose.picker.rememberWheelPickerState
import com.adamglin.zithian.compose.text.Text
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun Pickers() {
    Column {
        SmallTitle("Pickers")
        val candidates = persistentListOf("Cream/Tube", "Patch", "Eye Drops", "Nasal Spray", "Inhaler")
        val state = rememberWheelPickerState()
        WheelPicker(
            modifier = Modifier.height(52.dp),
            effect = WheelPickerEffect.Default.copy(minScale = 0.9f),
            state = state,
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {

        items(candidates) {
                Text(it)
            }
        }
    }
}