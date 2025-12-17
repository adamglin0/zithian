package com.adamglin.zithian.example.screens.features.config.sheets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.adamglin.composecontinuousroundedcornershape.ContinuousRoundedCornerShape
import com.adamglin.zithian.compose.button.PrimaryButton
import com.adamglin.zithian.compose.scaffold.SimpleSheetScaffold
import com.adamglin.zithian.compose.sheets.BottomSheet
import com.adamglin.zithian.compose.text.Text
import com.adamglin.zithian.compose.theme.ZithianTheme
import com.adamglin.zithian.example.screens.LocalExampleAppFontFamily

/**
 * Example demonstrating SimpleSheetScaffold with floating close button.
 * Shows how closeButtonEdgePadding can be used for radius-aware content layout.
 */
@Composable
fun SimpleSheetScaffoldExampleBottomSheet(
    isVisible: Boolean,
    onDismissRequest: () -> Unit
) {
    if (isVisible) {
        BottomSheet(
            onDismissRequest = { onDismissRequest() },
        ) {
            SimpleSheetScaffold(
                containerRadius = containerRadius,
                onClose = { onDismissRequest() },
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        // Use closeButtonEdgePadding for consistent padding
                        .padding(closeButtonEdgePadding)
                        .background(Color.Red),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Title area - uses closeButtonEdgePadding to avoid overlap with close button
                    Text(
                        text = "SimpleSheetScaffold",
                        style = ZithianTheme.typography.titleMedium,
                        fontFamily = LocalExampleAppFontFamily.current.montserrat,
                        modifier = Modifier.padding(end = closeButtonEdgePadding) // Extra padding for title
                    )

                    // Info section
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "Container Radius: $containerRadius",
                            style = ZithianTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Close Button Edge Padding: $closeButtonEdgePadding",
                            style = ZithianTheme.typography.bodyMedium
                        )
                    }

                    // Radius-aware content box
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .clip(ContinuousRoundedCornerShape(containerRadius / 2))
                            .background(ZithianTheme.colors.surface),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Content with Floating Close",
                                style = ZithianTheme.typography.markLarge
                            )
                            Text(
                                text = "Close button overlays this content",
                                style = ZithianTheme.typography.bodySmall
                            )
                        }
                    }

                    // Action button
                    PrimaryButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Done")
                    }
                }
            }
        }
    }
}
