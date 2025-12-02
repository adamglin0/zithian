package com.adamglin.zithian.compose.icon

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.adamglin.zithian.compose.theme.ZithianTheme

@Composable
fun BasicIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    dimens: IconButtonDimens = IconButtonDefaults.dimens(),
    enabled: Boolean = true,
    content: @Composable () -> Unit,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
        dimens = dimens,
        backgroundColor = ZithianTheme.colors.text2,
        foregroundColor = ZithianTheme.colors.text15,
        pressedBackgroundColor = ZithianTheme.colors.text1,
        pressedForegroundColor = ZithianTheme.colors.text15,
        enabled = enabled,
        content = content
    )
}

