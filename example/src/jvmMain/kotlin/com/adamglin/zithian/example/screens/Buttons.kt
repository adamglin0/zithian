package com.adamglin.zithian.example.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.adamglin.zithian.compose.button.NeutralButton
import com.adamglin.zithian.compose.button.PrimaryButton
import com.adamglin.zithian.compose.button.TextButton
import com.adamglin.zithian.compose.icon.CoilIcon
import com.adamglin.zithian.compose.layout.Gap
import com.adamglin.zithian.compose.text.Text
import zithian.example.generated.resources.Res

@Composable
fun Buttons() {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        BasicText(text = "Primary Buttons")
        FlowRow(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            PrimaryButton(
                onClick = {},
            ) { Text("Night Shift") }

            PrimaryButton(
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

            PrimaryButton(
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
                Text("With Leading & Trailing")
            }

            PrimaryButton(
                onClick = {},
                enabled = false,
            ) { Text("I am diabled") }
        }

        Gap(size = 10.dp)

        // Brand Styled Buttons
        BasicText(text = "Neutral Buttons")
        FlowRow(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            NeutralButton(
                onClick = {},
            ) { Text("Brand Button") }

            NeutralButton(
                onClick = {},
                leading = {
                    CoilIcon(
                        uri = Res.getUri("drawable/ic_folder_filled.svg"),
                        contentDescription = null,
                    )
                }
            ) {
                Text("With Icon")
            }
        }

        Gap(size = 10.dp)

        Gap(size = 10.dp)

        // Text Buttons
        BasicText(text = "Text Buttons")
        FlowRow(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            TextButton(
                onClick = {},
            ) { Text("Text Button") }

            TextButton(
                onClick = {},
                leading = {
                    CoilIcon(
                        uri = Res.getUri("drawable/ic_folder_filled.svg"),
                        contentDescription = null,
                    )
                }
            ) {
                Text("With Icon")
            }
        }
    }
}
