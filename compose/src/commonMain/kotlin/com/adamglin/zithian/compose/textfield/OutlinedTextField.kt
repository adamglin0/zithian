package com.adamglin.zithian.compose.textfield

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.adamglin.composecontinuousroundedcornershape.ContinuousRoundedCornerShape
import com.adamglin.zithian.compose.text.LocalTextStyle
import com.adamglin.zithian.compose.text.Text
import com.adamglin.zithian.compose.theme.InteractType
import com.adamglin.zithian.compose.theme.LocalInteractType
import com.adamglin.zithian.compose.theme.ZithianTheme

@Immutable
data class TextFieldDimens(
    val contentPadding: PaddingValues
) {
    companion object {
        internal val Pointer = TextFieldDimens(
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
        )

        internal val Touch = TextFieldDimens(
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp)
        )

        fun of(interactType: InteractType): TextFieldDimens {
            return when (interactType) {
                InteractType.Pointer -> Pointer
                InteractType.Touch -> Touch
            }
        }
    }
}

object TextFieldDefaults {
    @Composable
    fun dimens(
        interactType: InteractType = LocalInteractType.current
    ): TextFieldDimens = TextFieldDimens.of(interactType)
}

@Composable
fun OutlinedTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    placeholderText: String? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    inputTransformation: InputTransformation? = null,
    textStyle: TextStyle = LocalTextStyle.current,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onKeyboardAction: KeyboardActionHandler? = null,
    lineLimits: TextFieldLineLimits = TextFieldLineLimits.Default,
    onTextLayout: (Density.(getResult: () -> TextLayoutResult?) -> Unit)? = null,
    interactionSource: MutableInteractionSource? = null,
    cursorBrush: Brush = SolidColor(Color.Black),
    outputTransformation: OutputTransformation? = null,
    scrollState: ScrollState = rememberScrollState(),
    dimens: TextFieldDimens = TextFieldDefaults.dimens(),
) {
    BasicTextField(
        state,
        modifier,
        enabled,
        readOnly,
        inputTransformation,
        textStyle,
        keyboardOptions,
        onKeyboardAction,
        lineLimits,
        onTextLayout,
        interactionSource,
        cursorBrush,
        outputTransformation,
        decorator = {
            val shape = ContinuousRoundedCornerShape(20.dp)
            Box(
                modifier = Modifier
                    .border(1.dp, ZithianTheme.colors.border, shape)
                    .background(ZithianTheme.colors.surface, shape)
                    .padding(dimens.contentPadding)
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
        },
        scrollState,
    )
}
