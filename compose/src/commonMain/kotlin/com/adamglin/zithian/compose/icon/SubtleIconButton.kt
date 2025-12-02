package com.adamglin.zithian.compose.icon

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.adamglin.zithian.compose.theme.LocalZithianColors
import com.adamglin.zithian.compose.theme.ZithianTheme

@Composable
fun SubtleIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    dimens: IconButtonDimens = IconButtonDefaults.dimens(),
    enabled: Boolean = true,
    content: @Composable () -> Unit,
) {
    val foregroundColor = LocalZithianColors.current.text14
    IconButton(
        onClick = onClick,
        modifier = modifier,
        dimens = dimens,
        backgroundColor = Color.Transparent,
        foregroundColor = foregroundColor,
        pressedBackgroundColor = ZithianTheme.colors.subtlePressed,
        pressedForegroundColor = foregroundColor,
        enabled = enabled,
        content = content
    )
}
