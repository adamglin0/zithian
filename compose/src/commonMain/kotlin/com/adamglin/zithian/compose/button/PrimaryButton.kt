package com.adamglin.zithian.compose.button

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.adamglin.zithian.compose.text.LocalTextStyle
import com.adamglin.zithian.compose.theme.LocalZithianColors
import com.adamglin.zithian.compose.theme.ZithianTheme
import io.github.fletchmckee.liquid.LiquidState

@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    dimens: BasicButtonDimens = BasicButtonDefaults.dimens(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: BasicButtonColors = BasicButtonDefaults.colors(
        backgroundColor = LocalZithianColors.current.primary,
        foregroundColor = LocalZithianColors.current.text14,
        pressedBackgroundColor = ZithianTheme.colors.primaryBold,
        pressedForegroundColor = ZithianTheme.colors.text15
    ),
    textStyle: TextStyle = LocalTextStyle.current,
    leading: (@Composable () -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
    liquidState: LiquidState? = null,
    content: @Composable () -> Unit
) {
    BasicButton(
        onClick = onClick,
        modifier = modifier,
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
