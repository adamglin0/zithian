package com.adamglin.zithian.compose.button

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.adamglin.zithian.compose.text.LocalTextStyle
import com.adamglin.zithian.compose.theme.LocalZithianColors
import com.adamglin.zithian.compose.theme.ZithianTheme

@Composable
fun SubtleButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    dimens: BasicButtonDimens = BasicButtonDefaults.dimens(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    backgroundColor: Color = Color.Transparent,
    foregroundColor: Color = LocalZithianColors.current.text14,
    pressedBackgroundColor: Color = ZithianTheme.colors.primaryBold,
    pressedForegroundColor: Color = ZithianTheme.colors.text15,
    textStyle: TextStyle = LocalTextStyle.current,
    leading: (@Composable () -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
    content: @Composable () -> Unit
) {
    BasicButton(
        onClick = onClick,
        modifier = modifier,
        dimens = dimens,
        interactionSource = interactionSource,
        backgroundColor = backgroundColor,
        foregroundColor = foregroundColor,
        pressedBackgroundColor = pressedBackgroundColor,
        pressedForegroundColor = pressedForegroundColor,
        textStyle = textStyle,
        leading = leading,
        trailing = trailing,
        enabled = enabled,
        content = content
    )
}
