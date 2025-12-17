package com.adamglin.zithian.example.screens.features.config.sheets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.adamglin.composecontinuousroundedcornershape.ContinuousRoundedCornerShape
import com.adamglin.zithian.compose.button.PrimaryButton
import com.adamglin.zithian.compose.scaffold.SheetScaffold
import com.adamglin.zithian.compose.sheets.ModalBottomSheet
import com.adamglin.zithian.compose.sheets.header.TitleAndCloseSheetTitle
import com.adamglin.zithian.compose.text.Text
import com.adamglin.zithian.compose.theme.ZithianTheme
import com.adamglin.zithian.example.screens.LocalExampleAppFontFamily

/**
 * Example demonstrating SheetScaffold with header, bottom, and content areas.
 * Shows how containerRadius can be accessed for radius-aware styling.
 */
@Composable
fun SheetScaffoldExampleBottomSheet(
    isVisible: Boolean,
    onDismissRequest: () -> Unit
) {
    if (isVisible) {
        ModalBottomSheet(
            onDismissRequest = { onDismissRequest() },
        ) {
            SheetScaffold(
                containerRadius = containerRadius,
                header = {
                    TitleAndCloseSheetTitle(
                        title = {
                            Text(
                                "SheetScaffold Demo",
                                fontFamily = LocalExampleAppFontFamily.current.montserrat
                            )
                        },
                        onClose = { onDismissRequest() }
                    )
                },
                bottom = {
                    // Bottom area with radius-aware corners
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = containerRadius / 2)
                            .padding(bottom = containerRadius / 2)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            PrimaryButton(
                                onClick = { onDismissRequest() },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Cancel")
                            }
                            PrimaryButton(
                                onClick = { onDismissRequest() },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Confirm")
                            }
                        }
                    }
                }
            ) {
                // Content area demonstrating containerRadius awareness
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = headerHeight)
                        .padding(horizontal = 16.dp)
                        .padding(bottom = bottomHeight + 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Container Radius: $containerRadius",
                        style = ZithianTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Header Height: $headerHeight",
                        style = ZithianTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Bottom Height: $bottomHeight",
                        style = ZithianTheme.typography.bodyMedium
                    )

                    // Radius-aware content box
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .clip(ContinuousRoundedCornerShape(containerRadius / 2))
                            .background(ZithianTheme.colors.surface),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Radius-Aware Content",
                            style = ZithianTheme.typography.markLarge
                        )
                    }
                }
            }
        }
    }
}
