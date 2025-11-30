package com.adamglin.zithian.compose.button

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.adamglin.composecontinuousroundedcornershape.ContinuousRoundedCornerShape
import com.adamglin.zithian.compose.text.LocalTextStyle
import com.adamglin.zithian.compose.theme.LocalZithianColors
import dev.chrisbanes.haze.HazeState
import io.github.fletchmckee.liquid.LiquidState

@Composable
fun PrimaryStyledButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    dimens: BasicButtonDimens = BasicButtonDefaults.dimens(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    backgroundColor: Color = LocalZithianColors.current.primary,
    foregroundColor: Color = LocalZithianColors.current.text15,
    textStyle: TextStyle = LocalTextStyle.current,
    leading: (@Composable () -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
    content: @Composable () -> Unit
) {
    val backgroundColor = backgroundColor.copy(alpha = if (enabled) 1f else 0.5f)
    val foregroundColor = foregroundColor.copy(alpha = if (enabled) 1f else 0.5f)

    BasicButton(
        onClick = onClick,
        modifier = modifier,
        dimens = dimens,
        interactionSource = interactionSource,
        backgroundColor = backgroundColor,
        foregroundColor = foregroundColor,
        textStyle = textStyle,
        leading = leading,
        trailing = trailing,
        enabled = enabled,
        content = content
    )
}

