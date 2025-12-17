package com.adamglin.zithian.example.screens.features.config.sheets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.adamglin.composecontinuousroundedcornershape.ContinuousRoundedCornerShape
import com.adamglin.zithian.compose.button.PrimaryButton
import com.adamglin.zithian.compose.scaffold.SheetScaffold
import com.adamglin.zithian.compose.sheets.BottomSheet
import com.adamglin.zithian.compose.sheets.header.TitleAndCloseSheetTitle
import com.adamglin.zithian.compose.text.Text
import com.adamglin.zithian.compose.theme.ZithianTheme
import com.adamglin.zithian.example.screens.LocalExampleAppFontFamily

/**
 * Example demonstrating non-modal BottomSheet without scrim overlay.
 * The underlying content remains interactive while the sheet is visible.
 */
@Composable
fun NonModalBottomSheetExample(
    isVisible: Boolean,
    onDismissRequest: () -> Unit
) {
    if (isVisible) {
        BottomSheet(
            onDismissRequest = { onDismissRequest() },
        ) {
            SheetScaffold(
                containerRadius = containerRadius,
                header = {
                    TitleAndCloseSheetTitle(
                        title = {
                            Text(
                                "Non-Modal Sheet",
                                fontFamily = LocalExampleAppFontFamily.current.montserrat
                            )
                        },
                        onClose = { onDismissRequest() }
                    )
                },
                bottom = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = containerRadius / 2)
                            .padding(bottom = containerRadius / 2)
                    ) {
                        PrimaryButton(
                            onClick = { onDismissRequest() },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Close Sheet")
                        }
                    }
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = headerHeight)
                        .padding(horizontal = 16.dp)
                        .padding(bottom = bottomHeight + 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Info card
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(ContinuousRoundedCornerShape(containerRadius / 2))
                            .background(ZithianTheme.colors.primary.copy(alpha = 0.1f))
                            .padding(16.dp)
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(
                                text = "No Scrim Overlay",
                                style = ZithianTheme.typography.markMedium,
                                color = ZithianTheme.colors.primary
                            )
                            Text(
                                text = "This sheet has no dimmed background. You can still interact with the content behind this sheet.",
                                style = ZithianTheme.typography.bodyMedium
                            )
                        }
                    }

                    // Feature list
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(ContinuousRoundedCornerShape(containerRadius / 2))
                            .background(ZithianTheme.colors.surface)
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Features:",
                            style = ZithianTheme.typography.markMedium
                        )
                        FeatureItem("✓ No scrim/mask layer")
                        FeatureItem("✓ Background remains interactive")
                        FeatureItem("✓ Sheet doesn't block input")
                        FeatureItem("✓ Slide animation preserved")
                    }
                }
            }
        }
    }
}

@Composable
private fun FeatureItem(text: String) {
    Text(
        text = text,
        style = ZithianTheme.typography.bodyMedium,
        color = ZithianTheme.colors.text2
    )
}
