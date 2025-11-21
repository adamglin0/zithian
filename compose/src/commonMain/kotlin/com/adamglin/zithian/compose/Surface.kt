package com.adamglin.zithian.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import com.adamglin.zithian.compose.theme.LocalContentAlpha
import com.adamglin.zithian.compose.theme.LocalContentColor

@Composable
fun Surface(
    background: Color,
    foreground: Color,
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    textStyle: TextStyle = LocalTextStyle.current,
    contentAlpha: Float = 1f,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .clip(shape)
            .background(background)
    ) {
        CompositionLocalProvider(
            LocalContentColor provides foreground,
            LocalContentAlpha provides contentAlpha,
            LocalTextStyle provides textStyle,
        ) {
            content()
        }
    }
}