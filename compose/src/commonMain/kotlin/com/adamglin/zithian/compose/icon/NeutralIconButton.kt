package com.adamglin.zithian.compose.icon

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.adamglin.zithian.compose.theme.LocalZithianColors
import com.adamglin.zithian.compose.theme.ZithianTheme

@Composable
fun NeutralIconButton(
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
        backgroundColor = LocalZithianColors.current.neutral,
        foregroundColor = LocalZithianColors.current.onNeutral,
        pressedBackgroundColor = ZithianTheme.colors.neutralBold,
        pressedForegroundColor = ZithianTheme.colors.onNeutralBold,
        enabled = enabled,
        content = content
    )
}

