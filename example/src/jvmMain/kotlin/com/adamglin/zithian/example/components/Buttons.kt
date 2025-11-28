package com.adamglin.zithian.example.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.adamglin.zithian.compose.CoilIcon
import com.adamglin.zithian.compose.PrimaryStyledButton
import com.adamglin.zithian.compose.Text
import zithian.example.generated.resources.Res

@Composable
fun Buttons() {
    FlowRow(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        PrimaryStyledButton(
            onClick = {},
        ) { Text("Cancel") }

        PrimaryStyledButton(
            onClick = {},
            leading = {
                CoilIcon(
                    uri = Res.getUri("drawable/ic_folder_filled.svg"),
                    contentDescription = null,
                )
            }
        ) {
            Text("Open Folder")
        }

        PrimaryStyledButton(
            onClick = {},
            leading = {
                CoilIcon(
                    uri = Res.getUri("drawable/ic_folder_filled.svg"),
                    contentDescription = null,
                )
            },
            trailing = {
                CoilIcon(
                    uri = Res.getUri("drawable/ic_folder_filled.svg"),
                    contentDescription = null,
                )
            }
        ) {
            Text("Folders arroud me")
        }
    }
}