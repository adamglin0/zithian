package com.adamglin.zithian.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.adamglin.composecontinuousroundedcornershape.ContinuousRoundedCornerShape
import com.adamglin.zithian.compose.theme.LocalZithianColors

@Composable
fun BrandStyledButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = ContinuousRoundedCornerShape(20.dp),
    backgroundColor: Color = LocalZithianColors.current.primary,
    foregroundColor: Color = LocalZithianColors.current.text1,
    textStyle: TextStyle = LocalTextStyle.current,
    leading: (@Composable () -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null,
    dimens: BasicButtonDimens = BasicButtonDefaults.dimens(),
    content: @Composable () -> Unit
) {
    BasicButton(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
        backgroundColor = backgroundColor,
        foregroundColor = foregroundColor,
        textStyle = textStyle,
        leading = leading,
        trailing = trailing,
        dimens = dimens,
        content = content
    )
}
