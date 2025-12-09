package com.adamglin.zithian.compose.icon

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.adamglin.zithian.compose.theme.LocalZithianColors
import com.adamglin.zithian.compose.theme.ZithianTheme
import io.github.fletchmckee.liquid.LiquidState

@Composable
fun SubtleIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    dimens: IconButtonDimens = IconButtonDefaults.dimens(),
    enabled: Boolean = true,
    liquidState: LiquidState? = null,
    content: @Composable () -> Unit,
) {
    val foregroundColor = LocalZithianColors.current.text14
    IconButton(
        onClick = onClick,
        modifier = modifier,
        dimens = dimens,
        colors = IconButtonDefaults.colors(
            backgroundColor = Color.Transparent,
            foregroundColor = foregroundColor,
            pressedBackgroundColor = ZithianTheme.colors.subtlePressed,
            pressedForegroundColor = foregroundColor
        ),
        enabled = enabled,
        liquidState = liquidState,
        content = content
    )
}
