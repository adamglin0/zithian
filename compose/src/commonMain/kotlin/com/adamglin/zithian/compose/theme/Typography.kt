package com.adamglin.zithian.compose.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

val LocalZithianTypography = staticCompositionLocalOf<ZithianTypography> {
    error("No Zithian typography provided!")
}

@Immutable
data class ZithianTypography(
    val displayMedium: TextStyle,
    val displayLarge: TextStyle,
    val headlineSmall: TextStyle,
    val headlineMedium: TextStyle,
    val headlineLarge: TextStyle,
    val titleSmall: TextStyle,
    val titleMedium: TextStyle,
    val titleLarge: TextStyle,
    val titleExtraLarge: TextStyle,
    val bodyExtraSmall: TextStyle,
    val bodySmall: TextStyle,
    val bodyMedium: TextStyle,
    val bodyLarge: TextStyle,
    val markExtraSmall: TextStyle,
    val markSmall: TextStyle,
    val markMedium: TextStyle,
    val markLarge: TextStyle,
    val linkSmall: TextStyle,
    val linkMedium: TextStyle,
    val linkLarge: TextStyle,
) {
    companion object
}

val ZithianTypography.Companion.compat: ZithianTypography
    get() = ZithianTypography(
        // Display: Large promotional text, hero sections
        // Tight line height for visual impact
        displayMedium = TextStyle(
            fontSize = 48.sp,
            lineHeight = 1.2.em,
            fontWeight = FontWeight.Normal,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            )
        ),
        displayLarge = TextStyle(
            fontSize = 64.sp,
            lineHeight = 1.15.em,
            fontWeight = FontWeight.Normal,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            )
        ),
        // Headline: Page titles, major sections
        // Slightly relaxed line height for readability
        headlineSmall = TextStyle(
            fontSize = 24.sp,
            lineHeight = 1.3.em,
            fontWeight = FontWeight.Normal,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            )
        ),
        headlineMedium = TextStyle(
            fontSize = 28.sp,
            lineHeight = 1.3.em,
            fontWeight = FontWeight.Normal,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            )
        ),
        headlineLarge = TextStyle(
            fontSize = 36.sp,
            lineHeight = 1.25.em,
            fontWeight = FontWeight.Normal,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            )
        ),
        // Title: Section headers, card titles, emphasis
        // SemiBold for hierarchy distinction
        titleSmall = TextStyle(
            fontSize = 14.sp,
            lineHeight = 1.4.em,
            fontWeight = FontWeight.SemiBold,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            )
        ),
        titleMedium = TextStyle(
            fontSize = 16.sp,
            lineHeight = 1.4.em,
            fontWeight = FontWeight.SemiBold,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            )
        ),
        titleLarge = TextStyle(
            fontSize = 20.sp,
            lineHeight = 1.4.em,
            fontWeight = FontWeight.SemiBold,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            )
        ),
        titleExtraLarge = TextStyle(
            fontSize = 24.sp,
            lineHeight = 1.35.em,
            fontWeight = FontWeight.SemiBold,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            )
        ),
        // Body: Main content text, paragraphs
        // Comfortable line height for extended reading
        bodyExtraSmall = TextStyle(
            fontSize = 10.sp,
            lineHeight = 1.6.em,
            fontWeight = FontWeight.Normal,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            )
        ),
        bodySmall = TextStyle(
            fontSize = 12.sp,
            lineHeight = 1.5.em,
            fontWeight = FontWeight.Normal,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            )
        ),
        bodyMedium = TextStyle(
            fontSize = 14.sp,
            lineHeight = 1.5.em,
            fontWeight = FontWeight.Normal,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            )
        ),
        bodyLarge = TextStyle(
            fontSize = 16.sp,
            lineHeight = 1.5.em,
            fontWeight = FontWeight.Normal,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            )
        ),
        // Mark: Labels, badges, tags, captions
        // Medium weight for visual distinction at small sizes
        markExtraSmall = TextStyle(
            fontSize = 9.sp,
            lineHeight = 1.4.em,
            fontWeight = FontWeight.Medium,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            )
        ),
        markSmall = TextStyle(
            fontSize = 10.sp,
            lineHeight = 1.4.em,
            fontWeight = FontWeight.Medium,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            )
        ),
        markMedium = TextStyle(
            fontSize = 11.sp,
            lineHeight = 1.4.em,
            fontWeight = FontWeight.Medium,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            )
        ),
        markLarge = TextStyle(
            fontSize = 12.sp,
            lineHeight = 1.4.em,
            fontWeight = FontWeight.Medium,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            )
        ),
        // Link: Interactive text, navigation
        // Normal weight, styling handled by color
        linkSmall = TextStyle(
            fontSize = 12.sp,
            lineHeight = 1.5.em,
            fontWeight = FontWeight.Normal,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            )
        ),
        linkMedium = TextStyle(
            fontSize = 14.sp,
            lineHeight = 1.5.em,
            fontWeight = FontWeight.Normal,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            )
        ),
        linkLarge = TextStyle(
            fontSize = 16.sp,
            lineHeight = 1.5.em,
            fontWeight = FontWeight.Normal,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            )
        ),
    )