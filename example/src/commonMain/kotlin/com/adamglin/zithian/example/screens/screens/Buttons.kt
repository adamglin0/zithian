package com.adamglin.zithian.example.screens.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.adamglin.zithian.compose.button.*
import com.adamglin.zithian.compose.icon.*
import com.adamglin.zithian.compose.layout.Gap
import com.adamglin.zithian.compose.text.Text
import io.github.fletchmckee.liquid.liquefiable
import io.github.fletchmckee.liquid.rememberLiquidState
import zithian.example.generated.resources.Res

@Composable
fun Buttons() {
    val liquidState = rememberLiquidState()
    AsyncImage(
        modifier = Modifier.fillMaxSize().liquefiable(liquidState),
        model = Res.getUri("drawable/img_background_1.jpeg"),
        contentScale = ContentScale.Crop,
        contentDescription = null,
    )
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        BasicText(text = "Primary Buttons")
        FlowRow(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            PrimaryButton(
                onClick = {},
                liquidState = liquidState,
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

        BasicText(text = "Subtle Buttons")
        FlowRow(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            SubtleButton(
                onClick = {},
            ) { Text("Subtle Button") }

            SubtleButton(
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

        BasicText(text = "Outlined Buttons")
        FlowRow(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            OutlinedButton(
                onClick = {},
            ) { Text("Outlined Button") }

            OutlinedButton(
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
        BasicText(text = "Icon Buttons")
        FlowRow(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            BasicIconButton(
                onClick = {},
            ) {
                CoilIcon(
                    uri = Res.getUri("drawable/ic_folder_filled.svg"),
                    contentDescription = null,
                )
            }
            NeutralIconButton(
                onClick = {},
            ) {
                CoilIcon(
                    uri = Res.getUri("drawable/ic_folder_filled.svg"),
                    contentDescription = null,
                )
            }
            PrimaryIconButton(
                onClick = {},
            ) {
                CoilIcon(
                    uri = Res.getUri("drawable/ic_folder_filled.svg"),
                    contentDescription = null,
                )
            }
            SubtleIconButton(
                onClick = {},
            ) {
                CoilIcon(
                    uri = Res.getUri("drawable/ic_folder_filled.svg"),
                    contentDescription = null,
                )
            }
            OutlinedIconButton(
                onClick = {},
            ) {
                CoilIcon(
                    uri = Res.getUri("drawable/ic_folder_filled.svg"),
                    contentDescription = null,
                )
            }
        }
    }
}
