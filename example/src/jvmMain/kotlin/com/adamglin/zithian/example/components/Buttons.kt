package com.adamglin.zithian.example.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.adamglin.zithian.compose.button.BrandStyledButton
import com.adamglin.zithian.compose.button.PrimaryStyledButton
import com.adamglin.zithian.compose.button.SupportStyledButton
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
        // Primary Styled Buttons
        BasicText(text = "Primary Styled Buttons")
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
                Text("With Leading & Trailing")
            }
        }

        Gap(size = 10.dp)

        // Brand Styled Buttons
        BasicText(text = "Brand Styled Buttons")
        FlowRow(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            BrandStyledButton(
                onClick = {},
            ) { Text("Brand Button") }

            BrandStyledButton(
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

        // Support Styled Buttons
        BasicText(text = "Support Styled Buttons")
        FlowRow(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            SupportStyledButton(
                onClick = {},
            ) { Text("Support Button") }

            SupportStyledButton(
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
