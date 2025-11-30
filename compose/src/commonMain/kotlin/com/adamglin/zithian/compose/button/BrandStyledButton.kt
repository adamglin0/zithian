package com.adamglin.zithian.compose.button

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.adamglin.composecontinuousroundedcornershape.ContinuousRoundedCornerShape
import com.adamglin.zithian.compose.text.LocalTextStyle
import com.adamglin.zithian.compose.theme.LocalZithianColors

@Composable
fun BrandStyledButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    dimens: BasicButtonDimens = BasicButtonDefaults.dimens(),
    backgroundColor: Color = LocalZithianColors.current.primary,
    foregroundColor: Color = LocalZithianColors.current.text1,
    textStyle: TextStyle = LocalTextStyle.current,
    leading: (@Composable () -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit
) {
    BasicButton(
        onClick = onClick,
        modifier = modifier,
        backgroundColor = backgroundColor,
        foregroundColor = foregroundColor,
        textStyle = textStyle,
        leading = leading,
        trailing = trailing,
        dimens = dimens,
        content = content
    )
}

