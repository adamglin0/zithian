package com.adamglin.zithian.compose.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.adamglin.zithian.compose.theme.ZithianTheme

@Composable
fun VerticalDivider(
    modifier: Modifier = Modifier,
    color: Color = ZithianTheme.colors.border,
) {
    Spacer(
        modifier = modifier
            .width(1.dp)
            .fillMaxHeight()
            .background(color)
    )
}