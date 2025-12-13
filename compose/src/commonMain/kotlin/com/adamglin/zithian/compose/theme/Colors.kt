package com.adamglin.zithian.compose.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val LocalZithianColors = staticCompositionLocalOf {
    ZithianColors.light
}

@Immutable
data class ZithianColors(
    val brandColors: BrandColors = BrandColors,
    val greenColors: GreenColors = GreenColors,
    // Background colors
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val onSurface: Color,
    val surfacePure: Color,

    // Primary colors (using green from brand colors)
    val primary: Color,
    val primaryBold: Color,
    val primaryVariant: Color,
    val focusColor: Color,
    val onPrimary: Color,

    // Neutral
    val neutral: Color,
    val onNeutral: Color,
    val neutralBold: Color,
    val onNeutralBold: Color,
    // Text colors
    val text1: Color, // Primary text
    val text2: Color, // Secondary text
    val text3: Color, // Tertiary text
    val text4: Color, // Disabled text
    val text5: Color,
    val text6: Color,
    val text7: Color,
    val text8: Color,
    val text9: Color,
    val text10: Color,
    val text11: Color,
    val text12: Color,
    val text13: Color,
    val text14: Color,
    val text15: Color,

    // Common functional colors
    val success: Color,
    val onSuccess: Color,
    val error: Color,
    val onError: Color,
    val warning: Color,
    val onWarning: Color,
    val link: Color,

    // Border and divider
    val border: Color,
    val divider: Color,

    val subtlePressed: Color,

    val shadow: Color = Color.Black.copy(alpha = 0.05f),
) {
    companion object {
        val light = ZithianColors(
            // Background
            background = GrayColors.gray2,
            onBackground = GrayColors.gray14,
            surface = Color(0xFFFAFAFA),
            onSurface = GrayColors.gray14,
            surfacePure = Color.White,

            // Primary (using green from brand colors)
            primary = BrandColors.blue6, // greenColors.green5
            primaryBold = BrandColors.blue8,
            neutral = GrayColors.gray3,
            onNeutral = GrayColors.gray13,
            neutralBold = GrayColors.gray6,
            onNeutralBold = GrayColors.gray13,
            primaryVariant = Color(0xFF008858), // greenColors.green6
            onPrimary = Color.White,
            focusColor = Color(0x7f0DB4D6),
            // Text colors
            text1 = GrayColors.gray14,
            text2 = GrayColors.gray13,
            text3 = GrayColors.gray12,
            text4 = GrayColors.gray11,
            text5 = GrayColors.gray10,
            text6 = GrayColors.gray9,
            text7 = GrayColors.gray8,
            text8 = GrayColors.gray7,
            text9 = GrayColors.gray6,
            text10 = GrayColors.gray5,
            text11 = GrayColors.gray4,
            text12 = GrayColors.gray3,
            text13 = GrayColors.gray2,
            text14 = GrayColors.gray1,
            text15 = GrayColors.white,

            // Functional colors
            success = Color(0xFF2ba471), // Same as primary green
            onSuccess = Color.White,
            error = Color(0xFFd54941), // redColors.red6
            onError = Color.White,
            warning = Color(0xFFe37318), // orangeColors.orange5
            onWarning = Color.White,
            link = Color(0xFF0052d9), // brandColors.blue7

            // Border and divider
            border = Color(0xFFE8E8E8),
            divider = Color(0xFFF0F0F1),
            subtlePressed = GrayColors.gray2,
        )

        val dark = ZithianColors(
            // Background
            background = Color.Black,
            onBackground = GrayColors.white,
            surface = GrayColors.gray13,
            onSurface = GrayColors.white,
            surfacePure = GrayColors.gray10,

            // Primary (using green from brand colors)
            primary = BrandColors.blue4, // greenColors.green5
            primaryBold = Color(0xFF00A0E5),
            primaryVariant = Color(0xFF008858), // greenColors.green6
            neutral = GrayColors.gray13,
            onNeutral = GrayColors.gray1,
            neutralBold = GrayColors.gray10,
            onNeutralBold = GrayColors.gray1,
            onPrimary = Color.White,
            focusColor = Color(0xffABF1FF),

            // Text colors
            text1 = GrayColors.white,
            text2 = GrayColors.gray1,
            text3 = GrayColors.gray2,
            text4 = GrayColors.gray3,
            text5 = GrayColors.gray4,
            text6 = GrayColors.gray5,
            text7 = GrayColors.gray6,
            text8 = GrayColors.gray7,
            text9 = GrayColors.gray8,
            text10 = GrayColors.gray9,
            text11 = GrayColors.gray10,
            text12 = GrayColors.gray11,
            text13 = GrayColors.gray12,
            text14 = GrayColors.gray13,
            text15 = GrayColors.gray14,


            // Functional colors
            success = Color(0xFF2ba471), // Same as primary green
            onSuccess = Color.White,
            error = Color(0xFFd54941), // redColors.red6
            onError = Color.White,
            warning = Color(0xFFe37318), // orangeColors.orange5
            onWarning = Color.White,
            link = Color(0xFF0052d9), // brandColors.blue7

            // Border and divider
            border = Color(0xFF797979),
            divider = Color(0xFF6D6D6D),
            subtlePressed = GrayColors.gray12,
        )
    }
}

object GrayColors {
    val white: Color = Color(0xFFFFFFFF)
    val gray1: Color = Color(0xFFF3F3F3)
    val gray2: Color = Color(0xFFEEEEEE)
    val gray3: Color = Color(0xFFE8E8E8)
    val gray4: Color = Color(0xFFDDDDDD)
    val gray5: Color = Color(0xFFC6C6C6)
    val gray6: Color = Color(0xFFA6A6A6)
    val gray7: Color = Color(0xFF8B8B8B)
    val gray8: Color = Color(0xFF777777)
    val gray9: Color = Color(0xFF5E5E5E)
    val gray10: Color = Color(0xFF4B4B4B)
    val gray11: Color = Color(0xFF393939)
    val gray12: Color = Color(0xFF2C2C2C)
    val gray13: Color = Color(0xFF242424)
    val gray14: Color = Color(0xFF181818)
}

object BrandColors {
    val blue1: Color = Color(0xFFf2f3ff)
    val blue2: Color = Color(0xFFd9e1ff)
    val blue3: Color = Color(0xFFb5c7ff)
    val blue4: Color = Color(0xFF8eabff)
    val blue5: Color = Color(0xFF618dff)
    val blue6: Color = Color(0xFF366ef4)
    val blue7: Color = Color(0xFF0052d9) // Main brand color
    val blue8: Color = Color(0xFF003cab)
    val blue9: Color = Color(0xFF002a7c)
    val blue10: Color = Color(0xFF001a57)
}

object RedColors {
    val red1: Color = Color(0xFFfff0ed)
    val red2: Color = Color(0xFFffd8d2)
    val red3: Color = Color(0xFFffb9b0)
    val red4: Color = Color(0xFFff9285)
    val red5: Color = Color(0xFFf6685d)
    val red6: Color = Color(0xFFd54941) // Main red color
    val red7: Color = Color(0xFFad352f)
    val red8: Color = Color(0xFF881f1c)
    val red9: Color = Color(0xFF68070a)
    val red10: Color = Color(0xFF490002)
}

object GreenColors {
    val green1: Color = Color(0xFFe3f9e9)
    val green2: Color = Color(0xFFc6f3d7)
    val green3: Color = Color(0xFF92dab2)
    val green4: Color = Color(0xFF56c08d)
    val green5: Color = Color(0xFF2ba471) // Main green color
    val green6: Color = Color(0xFF008858)
    val green7: Color = Color(0xFF006c45)
    val green8: Color = Color(0xFF005334)
    val green9: Color = Color(0xFF003b23)
    val green10: Color = Color(0xFF002515)
}

