package com.adamglin.zithian.example.screens.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adamglin.zithian.compose.button.PrimaryButton
import com.adamglin.zithian.compose.sheets.BasicSheet
import com.adamglin.zithian.compose.text.Text

@Composable
fun Sheets() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        var isSheetVisible by remember { mutableStateOf(false) }

        PrimaryButton(onClick = { isSheetVisible = true }) {
            Text("Open Sheet")
        }

        BasicSheet(
            isVisible = isSheetVisible,
            onDismissRequest = { isSheetVisible = false },
            header = {
                Text("Sheet Header", modifier = Modifier.padding(20.dp))
            }
        ) {
            Text("Sheet Content", modifier = Modifier.padding(20.dp))
        }
    }
}
