package com.adamglin.zithian.example.screens.features.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.adamglin.composecontinuousroundedcornershape.ContinuousRoundedCornerShape
import com.adamglin.zithian.compose.layout.BasicFiller
import com.adamglin.zithian.compose.scaffold.ScreenScaffold
import com.adamglin.zithian.compose.text.Text
import com.adamglin.zithian.compose.theme.ZithianTheme
import com.adamglin.zithian.example.screens.LocalExampleAppFontFamily

@Composable
internal fun ThemeScreen() {
    val colors = ZithianTheme.colors
    val typography = ZithianTheme.typography

    // Prepare typography items for display - names are derived from property names
    val typographyItems = remember(typography) {
        listOf(
            "displayLarge" to typography.displayLarge,
            "displayMedium" to typography.displayMedium,
            "headlineLarge" to typography.headlineLarge,
            "headlineMedium" to typography.headlineMedium,
            "headlineSmall" to typography.headlineSmall,
            "titleExtraLarge" to typography.titleExtraLarge,
            "titleLarge" to typography.titleLarge,
            "titleMedium" to typography.titleMedium,
            "titleSmall" to typography.titleSmall,
            "bodyLarge" to typography.bodyLarge,
            "bodyMedium" to typography.bodyMedium,
            "bodySmall" to typography.bodySmall,
            "bodyExtraSmall" to typography.bodyExtraSmall,
            "markLarge" to typography.markLarge,
            "markMedium" to typography.markMedium,
            "markSmall" to typography.markSmall,
            "markExtraSmall" to typography.markExtraSmall,
            "linkLarge" to typography.linkLarge,
            "linkMedium" to typography.linkMedium,
            "linkSmall" to typography.linkSmall,
        )
    }

    // Prepare color items grouped by category
    val colorGroups = remember(colors) {
        listOf(
            ColorGroup(
                "Background & Surface",
                listOf(
                    ColorItem("background", colors.background),
                    ColorItem("onBackground", colors.onBackground),
                    ColorItem("surface", colors.surface),
                    ColorItem("onSurface", colors.onSurface),
                    ColorItem("surfacePure", colors.surfacePure),
                )
            ),
            ColorGroup(
                "Primary",
                listOf(
                    ColorItem("primary", colors.primary),
                    ColorItem("primaryBold", colors.primaryBold),
                    ColorItem("primaryVariant", colors.primaryVariant),
                    ColorItem("onPrimary", colors.onPrimary),
                    ColorItem("focusColor", colors.focusColor),
                )
            ),
            ColorGroup(
                "Neutral",
                listOf(
                    ColorItem("neutral", colors.neutral),
                    ColorItem("onNeutral", colors.onNeutral),
                    ColorItem("neutralBold", colors.neutralBold),
                    ColorItem("onNeutralBold", colors.onNeutralBold),
                )
            ),
            ColorGroup(
                "Text",
                listOf(
                    ColorItem("text1", colors.text1),
                    ColorItem("text2", colors.text2),
                    ColorItem("text3", colors.text3),
                    ColorItem("text4", colors.text4),
                    ColorItem("text5", colors.text5),
                    ColorItem("text6", colors.text6),
                    ColorItem("text7", colors.text7),
                    ColorItem("text8", colors.text8),
                    ColorItem("text9", colors.text9),
                    ColorItem("text10", colors.text10),
                    ColorItem("text11", colors.text11),
                    ColorItem("text12", colors.text12),
                    ColorItem("text13", colors.text13),
                    ColorItem("text14", colors.text14),
                    ColorItem("text15", colors.text15),
                )
            ),
            ColorGroup(
                "Semantic",
                listOf(
                    ColorItem("success", colors.success),
                    ColorItem("onSuccess", colors.onSuccess),
                    ColorItem("error", colors.error),
                    ColorItem("onError", colors.onError),
                    ColorItem("warning", colors.warning),
                    ColorItem("onWarning", colors.onWarning),
                    ColorItem("link", colors.link),
                )
            ),
            ColorGroup(
                "Border & Divider",
                listOf(
                    ColorItem("border", colors.border),
                    ColorItem("divider", colors.divider),
                    ColorItem("subtlePressed", colors.subtlePressed),
                    ColorItem("shadow", colors.shadow),
                )
            ),
        )
    }

    ScreenScaffold(
        header = {
            Box(
                modifier = Modifier.fillMaxWidth()
                    .background(ZithianTheme.colors.surfacePure)
                    .padding(horizontal = 20.dp, vertical = 10.dp)
                    .statusBarsPadding()
            ) {
                Text(
                    text = "Theme",
                    style = ZithianTheme.typography.titleLarge,
                    fontFamily = LocalExampleAppFontFamily.current.montserrat,
                )
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item { BasicFiller(height = headerHeight) }
            item {
                SectionHeader(title = "Typography")
            }

            items(typographyItems) { (name, style) ->
                TypographyCard(name = name, style = style)
            }

            // Colors Section
            item {
                SectionHeader(
                    title = "Colors",
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            colorGroups.forEach { group ->
                item {
                    ColorGroupCard(group)
                }
            }
        }
    }
}

@Composable
private fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        style = ZithianTheme.typography.headlineMedium,
        modifier = modifier.padding(vertical = 8.dp)
    )
}

@Composable
private fun TypographyCard(name: String, style: TextStyle) {
    val colors = ZithianTheme.colors

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(ContinuousRoundedCornerShape(12.dp))
            .background(colors.surfacePure)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // Display the style name using its own style - "Show, don't tell"
        Text(
            text = name,
            style = style,
        )
        // Show the specification dynamically extracted from TextStyle
        Text(
            text = style.toSpecString(),
            style = ZithianTheme.typography.bodySmall,
            color = colors.text5,
        )
    }
}

// Dynamically extract spec info from TextStyle
private fun TextStyle.toSpecString(): String {
    val parts = mutableListOf<String>()

    // Font size
    if (fontSize.isSp) {
        parts.add("${fontSize.value.toInt()}sp")
    }

    // Font weight
    fontWeight?.let { weight ->
        val weightName = when (weight) {
            FontWeight.Thin -> "Thin"
            FontWeight.ExtraLight -> "ExtraLight"
            FontWeight.Light -> "Light"
            FontWeight.Normal -> null // Don't show normal as it's the default
            FontWeight.Medium -> "Medium"
            FontWeight.SemiBold -> "SemiBold"
            FontWeight.Bold -> "Bold"
            FontWeight.ExtraBold -> "ExtraBold"
            FontWeight.Black -> "Black"
            else -> "W${weight.weight}"
        }
        weightName?.let { parts.add(it) }
    }

    return parts.joinToString(" • ")
}

@Composable
private fun ColorGroupCard(group: ColorGroup) {
    val colors = ZithianTheme.colors

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(ContinuousRoundedCornerShape(12.dp))
            .background(colors.surfacePure)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Group title
        Text(
            text = group.name,
            style = ZithianTheme.typography.titleMedium,
        )

        // Color items in a grid-like layout
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            group.items.forEach { colorItem ->
                ColorRow(colorItem)
            }
        }
    }
}

@Composable
private fun ColorRow(item: ColorItem) {
    val colors = ZithianTheme.colors

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Color swatch with border for light colors
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(item.color)
                .border(
                    width = 1.dp,
                    color = colors.border,
                    shape = CircleShape
                )
        )

        // Color name
        Text(
            text = item.name,
            style = ZithianTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )

        // Hex value for reference
        Text(
            text = item.color.toHexString(),
            style = ZithianTheme.typography.bodySmall,
            color = colors.text5,
        )
    }
}

// Helper function to convert Color to hex string
private fun Color.toHexString(): String {
    val alpha = (this.alpha * 255).toInt()
    val red = (this.red * 255).toInt()
    val green = (this.green * 255).toInt()
    val blue = (this.blue * 255).toInt()

    return if (alpha == 255) {
        String.format("#%02X%02X%02X", red, green, blue)
    } else {
        String.format("#%02X%02X%02X%02X", alpha, red, green, blue)
    }
}

// Data classes for organizing theme items
private data class ColorItem(
    val name: String,
    val color: Color
)

private data class ColorGroup(
    val name: String,
    val items: List<ColorItem>
)
