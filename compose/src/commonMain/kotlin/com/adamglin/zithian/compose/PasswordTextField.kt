package com.adamglin.zithian.compose


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.adamglin.zithian.compose.theme.ZithianTheme

@Composable
fun PasswordTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    inputTransformation: InputTransformation? = null,
    textStyle: TextStyle = LocalTextStyle.current,
    onKeyboardAction: KeyboardActionHandler? = null,
    onTextLayout: (Density.(getResult: () -> TextLayoutResult?) -> Unit)? = null,
    interactionSource: MutableInteractionSource? = null,
    cursorBrush: Brush = SolidColor(Color.Black),
    textObfuscationMode: TextObfuscationMode = TextObfuscationMode.RevealLastTyped,
    textObfuscationCharacter: Char = '\u2022',
) {
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
            val shape = RoundedCornerShape(8.dp)
            Box(
                modifier = Modifier.Companion
                    .border(1.dp, ZithianTheme.colors.border, shape)
                    .background(ZithianTheme.colors.surface, shape)
                    .padding(20.dp, 12.dp)
            ) {
                it()
            }
        },
        textObfuscationMode = textObfuscationMode,
        textObfuscationCharacter = textObfuscationCharacter,
    )
}