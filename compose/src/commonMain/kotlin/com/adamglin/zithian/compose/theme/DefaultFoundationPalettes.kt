package com.adamglin.zithian.compose.theme

import androidx.compose.ui.graphics.Color

/**
 * "What colors are available?"
 */
class DefaultFoundationPalettes {
    val blue = TenColorsPalette(
        color1 = Color(0xFFf2f3ff),
        color2 = Color(0xFFd9e1ff),
        color3 = Color(0xFFb5c7ff),
        color4 = Color(0xFF8eabff),
        color5 = Color(0xFF618dff),
        color6 = Color(0xFF366ef4),
        color7 = Color(0xFF0052d9),
        color8 = Color(0xFF003cab),
        color9 = Color(0xFF002a7c),
        color10 = Color(0xFF001a57)
    )

    val red = TenColorsPalette(
        color1 = Color(0xFFfff0ed),
        color2 = Color(0xFFffd8d2),
        color3 = Color(0xFFffb9b0),
        color4 = Color(0xFFff9285),
        color5 = Color(0xFFf6685d),
        color6 = Color(0xFFd54941),
        color7 = Color(0xFFad352f),
        color8 = Color(0xFF881f1c),
        color9 = Color(0xFF68070a),
        color10 = Color(0xFF490002)
    )

    val orange = TenColorsPalette(
        color1 = Color(0xFFfff1e9),
        color2 = Color(0xFFffd9c2),
        color3 = Color(0xFFffb98c),
        color4 = Color(0xFFfa9550),
        color5 = Color(0xFFe37318),
        color6 = Color(0xFFbe5a00),
        color7 = Color(0xFF954500),
        color8 = Color(0xFF713300),
        color9 = Color(0xFF532300),
        color10 = Color(0xFF3b1700)
    )

    val green = TenColorsPalette(
        color1 = Color(0xFFe3f9e9),
        color2 = Color(0xFFc6f3d7),
        color3 = Color(0xFF92dab2),
        color4 = Color(0xFF56c08d),
        color5 = Color(0xFF2ba471),
        color6 = Color(0xFF008858),
        color7 = Color(0xFF006c45),
        color8 = Color(0xFF005334),
        color9 = Color(0xFF003b23),
        color10 = Color(0xFF002515)
    )

    val gray = TenColorsPalette(
        color1 = Color(0xFFf3f3f3),
        color2 = Color(0xFFeeeeee),
        color3 = Color(0xFFe8e8e8),
        color4 = Color(0xFFc6c6c6),
        color5 = Color(0xFFababab),
        color6 = Color(0xFF8b8b8b),
        color7 = Color(0xFF777777),
        color8 = Color(0xFF4b4b4b),
        color9 = Color(0xFF393939),
        color10 = Color(0xFF2c2c2c)
    )
}

data class TenColorsPalette(
    val color1: Color,
    val color2: Color,
    val color3: Color,
    val color4: Color,
    val color5: Color,
    val color6: Color,
    val color7: Color,
    val color8: Color,
    val color9: Color,
    val color10: Color
)