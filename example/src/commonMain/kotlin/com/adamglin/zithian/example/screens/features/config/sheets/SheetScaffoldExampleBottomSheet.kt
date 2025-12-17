package com.adamglin.zithian.example.screens.features.config.sheets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.adamglin.composecontinuousroundedcornershape.ContinuousRoundedCornerShape
import com.adamglin.zithian.compose.scaffold.SheetScaffold
import com.adamglin.zithian.compose.sheets.ModalBottomSheet
import com.adamglin.zithian.compose.sheets.header.TitleAndCloseSheetTitle
import com.adamglin.zithian.compose.slider.Slider
import com.adamglin.zithian.compose.slider.SliderDefaults
import com.adamglin.zithian.compose.slider.Thumb
import com.adamglin.zithian.compose.slider.rememberSliderState
import com.adamglin.zithian.compose.text.Text
import com.adamglin.zithian.compose.theme.ZithianTheme
import com.adamglin.zithian.compose.utils.calculateInnerCornerRadius
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
            ) {
                val sliderState = rememberSliderState(0f, 0f..32f)
                // Content area demonstrating containerRadius awareness
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = headerHeight)
                        .padding(horizontal = sliderState.value.dp)
                        .padding(bottom = bottomHeight + sliderState.value.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Text(
                        text = "Container Radius: $containerRadius",
                        style = ZithianTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Padding: ${sliderState.value}",
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
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .clip(
                                ContinuousRoundedCornerShape(
                                    calculateInnerCornerRadius(
                                        containerRadius,
                                        sliderState.value.dp
                                    )
                                )
                            )
                            .background(ZithianTheme.colors.success),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Radius-Aware Content",
                            style = ZithianTheme.typography.markLarge
                        )
                        Slider(
                            state = sliderState,
                            track = {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(4.dp)
                                        .background(ZithianTheme.colors.surface, CircleShape)
                                )
                            },
                            thumb = {
                                Thumb(
                                    shape = CircleShape,
                                    color = ZithianTheme.colors.primary,
                                    dimens = SliderDefaults.dimens()
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}
