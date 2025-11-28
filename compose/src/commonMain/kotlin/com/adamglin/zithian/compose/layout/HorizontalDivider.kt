package com.adamglin.zithian.compose.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.adamglin.zithian.compose.theme.ZithianTheme

@Composable
fun HorizontalDivider(
    modifier: Modifier = Modifier,
    color: Color = ZithianTheme.colors.divider,
) {
    Spacer(
        modifier = modifier
            .height(1.dp)
            .fillMaxWidth()
            .background(color)
    )
}