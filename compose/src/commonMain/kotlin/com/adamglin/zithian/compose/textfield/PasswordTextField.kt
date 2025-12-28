package com.adamglin.zithian.compose.textfield


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.adamglin.composecontinuousroundedcornershape.ContinuousRoundedCornerShape
import com.adamglin.zithian.compose.generated.resources.ZithianResources
import com.adamglin.zithian.compose.icon.CoilIcon
import com.adamglin.zithian.compose.text.LocalTextStyle
import com.adamglin.zithian.compose.text.Text
import com.adamglin.zithian.compose.theme.ZithianTheme

@Composable
fun PasswordTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    placeholderText: String? = null,
    enabled: Boolean = true,
    inputTransformation: InputTransformation? = null,
    textStyle: TextStyle = LocalTextStyle.current,
    onKeyboardAction: KeyboardActionHandler? = null,
    onTextLayout: (Density.(getResult: () -> TextLayoutResult?) -> Unit)? = null,
    interactionSource: MutableInteractionSource? = null,
    cursorBrush: Brush = SolidColor(Color.Black),
    textObfuscationMode: TextObfuscationMode = TextObfuscationMode.RevealLastTyped,
    textObfuscationCharacter: Char = '\u2022',
    dimens: TextFieldDimens = TextFieldDefaults.dimens(),
) {
    var isPasswordVisible by retain { mutableStateOf(false) }
    val mTextObfuscationMode = if (isPasswordVisible) TextObfuscationMode.Visible else textObfuscationMode
    BasicSecureTextField(
        state,
        modifier,
        enabled,
        onKeyboardAction = onKeyboardAction,
        onTextLayout = onTextLayout,
        interactionSource = interactionSource,
        cursorBrush = cursorBrush,
        textStyle = textStyle,
        inputTransformation = inputTransformation,
        decorator = {

            val shape = ContinuousRoundedCornerShape(20.dp)
            Row(
                modifier = Modifier
                    .border(1.dp, ZithianTheme.colors.border, shape)
                    .background(ZithianTheme.colors.surface, shape)
                    .padding(dimens.contentPadding),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    if (placeholderText != null && state.text.isEmpty()) {
                        Text(
                            text = placeholderText,
                            style = textStyle,
                            color = ZithianTheme.colors.text10,
                        )
                    }
                    it()
                }
                VisibleSwitch(
                    isVisible = isPasswordVisible,
                    onClick = { isPasswordVisible = !isPasswordVisible },
                    modifier = Modifier.size(17.dp)
                )
            }
        },
        textObfuscationMode = mTextObfuscationMode,
        textObfuscationCharacter = textObfuscationCharacter,
    )
}

@Composable
private fun VisibleSwitch(
    isVisible: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val icon =
        if (isVisible) ZithianResources.getUri("drawable/ic_eye.svg")
        else ZithianResources.getUri("drawable/ic_eye-closed.svg")
    CoilIcon(
        modifier = modifier.clickable { onClick() },
        uri = icon,
        contentDescription = null,
    )
}