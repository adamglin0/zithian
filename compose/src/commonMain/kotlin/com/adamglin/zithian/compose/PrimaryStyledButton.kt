package com.adamglin.zithian.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.adamglin.composecontinuousroundedcornershape.ContinuousRoundedCornerShape
import com.adamglin.zithian.compose.theme.LocalZithianColors
import dev.chrisbanes.haze.HazeState
import io.github.fletchmckee.liquid.LiquidState

@Composable
fun PrimaryStyledButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    liquidState: LiquidState? = null,
    hazeState: HazeState? = null,
    shape: Shape = ContinuousRoundedCornerShape(20.dp),
    backgroundColor: Color = LocalZithianColors.current.primary,
    foregroundColor: Color = LocalZithianColors.current.text15,
    textStyle: TextStyle = LocalTextStyle.current,
    leading: (@Composable () -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null,
    contentPadding: PaddingValues = PaddingValues(20.dp, 12.dp),
    edgeInsetsInsteadPadding: Dp = 7.dp,
    enabled: Boolean = true,
    content: @Composable () -> Unit
) {
    val backgroundColor = backgroundColor.copy(alpha = if (enabled) 1f else 0.5f)
    val foregroundColor = foregroundColor.copy(alpha = if (enabled) 1f else 0.5f)

    BasicButton(
        onClick = onClick,
        modifier = modifier,
        liquidState = liquidState,
        hazeState = hazeState,
        shape = shape,
        backgroundColor = backgroundColor,
        foregroundColor = foregroundColor,
        textStyle = textStyle,
        leading = leading,
        trailing = trailing,
        contentPadding = contentPadding,
        edgeInsetsInsteadPadding = edgeInsetsInsteadPadding,
        enabled = enabled,
        content = content
    )
}