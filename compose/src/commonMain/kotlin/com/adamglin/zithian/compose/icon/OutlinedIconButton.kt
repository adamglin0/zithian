package com.adamglin.zithian.compose.icon

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.adamglin.composecontinuousroundedcornershape.ContinuousRoundedCornerShape
import com.adamglin.zithian.compose.theme.LocalZithianColors
import com.adamglin.zithian.compose.theme.ZithianTheme
import com.adamglin.zithian.compose.utils.innerBorder
import io.github.fletchmckee.liquid.LiquidState

@Composable
fun OutlinedIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    dimens: IconButtonDimens = IconButtonDefaults.dimens(),
    enabled: Boolean = true,
    liquidState: LiquidState? = null,
    content: @Composable () -> Unit,
) {
    val shape = remember(dimens.cornerRadius) { ContinuousRoundedCornerShape(dimens.cornerRadius) }
    IconButton(
        onClick = onClick,
        modifier = modifier.innerBorder(1.dp, ZithianTheme.colors.border, shape),
        dimens = dimens,
        backgroundColor = LocalZithianColors.current.surface,
        foregroundColor = LocalZithianColors.current.onSurface,
        pressedBackgroundColor = ZithianTheme.colors.neutralBold,
        pressedForegroundColor = ZithianTheme.colors.onNeutralBold,
        enabled = enabled,
        liquidState = liquidState,
        content = content
    )
}
