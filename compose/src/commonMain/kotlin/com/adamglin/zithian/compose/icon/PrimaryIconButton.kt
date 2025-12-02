package com.adamglin.zithian.compose.icon

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.adamglin.zithian.compose.theme.ZithianTheme

@Composable
fun PrimaryIconButton(
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
        backgroundColor = ZithianTheme.colors.primary,
        foregroundColor = ZithianTheme.colors.onPrimary,
        pressedBackgroundColor = ZithianTheme.colors.primaryBold,
        pressedForegroundColor = ZithianTheme.colors.text15,
        enabled = enabled,
        content = content
    )
}
