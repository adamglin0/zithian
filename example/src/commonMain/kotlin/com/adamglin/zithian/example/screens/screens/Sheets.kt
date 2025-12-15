package com.adamglin.zithian.example.screens.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adamglin.zithian.compose.button.PrimaryButton
import com.adamglin.zithian.compose.layout.BasicFiller
import com.adamglin.zithian.compose.sheets.BottomSheet
import com.adamglin.zithian.compose.text.Text
import com.adamglin.zithian.compose.theme.ZithianTheme

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

        BottomSheet(
            isVisible = isSheetVisible,
            onDismissRequest = { isSheetVisible = false },
            header = {
                Text(
                    "Sheet Preview",
                    style = ZithianTheme.typography.titleSmall,
                    modifier = Modifier.padding(20.dp)
                )
            }
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                BasicFiller(height = headerHeight)
                Text(
                    text = """
                        In the early hush before the day,
                        Shadows drift and fade away.
                        A single light begins to rise,
                        Painting gold across the skies,
                        Whispering softly: hope will stay.
                        In the early hush before the day,
                        Shadows drift and fade away.
                        A single light begins to rise,
                        Painting gold across the skies,
                        Whispering softly: hope will stay.
                        In the early hush before the day,
                        Shadows drift and fade away.
                        A single light begins to rise,
                        Painting gold across the skies,
                        Whispering softly: hope will stay.
                    """.trimIndent(),
                    modifier = Modifier.padding(20.dp)
                )
            }
        }
    }
}





