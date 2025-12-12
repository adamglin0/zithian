package com.adamglin.zithian.example.screens.features.components.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adamglin.zithian.compose.button.NeutralButton
import com.adamglin.zithian.compose.button.OutlinedButton
import com.adamglin.zithian.compose.button.PrimaryButton
import com.adamglin.zithian.compose.button.SubtleButton
import com.adamglin.zithian.compose.icon.CoilIcon
import com.adamglin.zithian.compose.text.Text
import zithian.example.generated.resources.Res

@Composable
internal fun Buttons(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        SmallTitle("Buttons")
        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            PrimaryButton(
                onClick = {},
            ) { Text("PrimaryButton") }
            PrimaryButton(
                onClick = {},
                leading = { LeadingExample() }
            ) { Text("PrimaryButton") }
            PrimaryButton(
                onClick = {},
                enabled = false,
            ) { Text("PrimaryButton") }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            NeutralButton(
                onClick = {},
            ) { Text("NeutralButton") }
            NeutralButton(
                onClick = {},
                leading = { LeadingExample() }
            ) { Text("NeutralButton") }
            NeutralButton(
                onClick = {},
                enabled = false,
            ) { Text("NeutralButton") }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            SubtleButton(
                onClick = {},
            ) { Text("SubtleButton") }
            SubtleButton(
                onClick = {},
                leading = { LeadingExample() }
            ) { Text("SubtleButton") }
            SubtleButton(
                onClick = {},
                enabled = false,
            ) { Text("SubtleButton") }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            OutlinedButton(
                onClick = {},
            ) { Text("OutlinedButton") }
            OutlinedButton(
                onClick = {},
                leading = { LeadingExample() }
            ) { Text("OutlinedButton") }
            OutlinedButton(
                onClick = {},
                enabled = false,
            ) { Text("OutlinedButton") }
        }
    }
}

@Composable
private fun LeadingExample() {
    CoilIcon(
        uri = Res.getUri("drawable/ic_butterfly_filled.svg"),
        contentDescription = null,
    )
}