package com.adamglin.zithian.compose.button

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.adamglin.composecontinuousroundedcornershape.ContinuousRoundedCornerShape
import com.adamglin.zithian.compose.text.LocalTextStyle
import com.adamglin.zithian.compose.theme.LocalZithianColors
import com.adamglin.zithian.compose.theme.ZithianTheme
import com.adamglin.zithian.compose.utils.innerBorder
import io.github.fletchmckee.liquid.LiquidState

@Composable
fun OutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    dimens: BasicButtonDimens = BasicButtonDefaults.dimens(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: BasicButtonColors = BasicButtonDefaults.colors(
        backgroundColor = LocalZithianColors.current.surface,
        foregroundColor = LocalZithianColors.current.onSurface,
        pressedBackgroundColor = ZithianTheme.colors.neutralBold,
        pressedForegroundColor = ZithianTheme.colors.onNeutralBold
    ),
    borderColor: Color = ZithianTheme.colors.border,
    textStyle: TextStyle = LocalTextStyle.current,
    leading: (@Composable () -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
    liquidState: LiquidState? = null,
    content: @Composable () -> Unit
) {
    val shape = remember(dimens.cornerRadius) { ContinuousRoundedCornerShape(dimens.cornerRadius) }
    BasicButton(
        onClick = onClick,
        modifier = modifier.innerBorder(1.dp, borderColor, shape),
        dimens = dimens,
        interactionSource = interactionSource,
        colors = colors,
        textStyle = textStyle,
        leading = leading,
        trailing = trailing,
        enabled = enabled,
        liquidState = liquidState,
        content = content
    )
}
