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
        displayMedium = TextStyle(
            fontSize = 48.sp, lineHeight = 1.5.em,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            )
        ),
        displayLarge = TextStyle(
            fontSize = 64.sp, lineHeight = 1.5.em,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            )
        ),
        headlineSmall = TextStyle(
            fontSize = 24.sp, lineHeight = 1.5.em,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            )
        ),
        headlineMedium = TextStyle(
            fontSize = 28.sp, lineHeight = 1.5.em,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            )
        ),
        headlineLarge = TextStyle(
            fontSize = 36.sp, lineHeight = 1.5.em,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            )
        ),
        titleSmall = TextStyle(
            fontSize = 14.sp, lineHeight = 1.5.em,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            )
        ),
        titleMedium = TextStyle(
            fontSize = 16.sp, lineHeight = 1.5.em,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            )
        ),
        titleLarge = TextStyle(
            fontSize = 21.sp,
            lineHeight = 1.5.em,
            fontWeight = FontWeight.SemiBold,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            )
        ),
        titleExtraLarge = TextStyle(
            fontSize = 20.sp, lineHeight = 1.5.em,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            )
        ),
        bodyExtraSmall = TextStyle(
            fontSize = 10.sp, lineHeight = 1.5.em,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            )
        ),
        bodySmall = TextStyle(
            fontSize = 12.sp, lineHeight = 1.5.em,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            )
        ),
        bodyMedium = TextStyle(
            fontSize = 14.sp, lineHeight = 1.5.em,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            )
        ),
        bodyLarge = TextStyle(
            fontSize = 16.sp, lineHeight = 1.5.em,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            )
        ),
        markExtraSmall = TextStyle(
            fontSize = 10.sp, lineHeight = 1.5.em,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            )
        ),
        markSmall = TextStyle(
            fontSize = 10.sp, lineHeight = 1.5.em,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            )
        ),
        markMedium = TextStyle(
            fontSize = 12.sp, lineHeight = 1.5.em,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            )
        ),
        markLarge = TextStyle(
            fontSize = 14.sp, lineHeight = 1.5.em,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            )
        ),
        linkSmall = TextStyle(
            fontSize = 12.sp, lineHeight = 1.5.em,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            )
        ),
        linkMedium = TextStyle(
            fontSize = 14.sp, lineHeight = 1.5.em,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            )
        ),
        linkLarge = TextStyle(
            fontSize = 16.sp, lineHeight = 1.5.em,
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            )
        ),
    )