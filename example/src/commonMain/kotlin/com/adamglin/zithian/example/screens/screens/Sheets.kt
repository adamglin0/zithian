package com.adamglin.zithian.example.screens.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.adamglin.zithian.compose.button.PrimaryButton
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
    }
}





