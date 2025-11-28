package com.adamglin.zithian.compose.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.adamglin.composecontinuousroundedcornershape.ContinuousRoundedCornerShape
import com.adamglin.zithian.compose.text.LocalTextStyle
import com.adamglin.zithian.compose.theme.InteractType
import com.adamglin.zithian.compose.theme.LocalInteractType
import com.adamglin.zithian.compose.theme.ZithianTheme

@Immutable
data class TextButtonDimens(
    val contentPadding: PaddingValues,
    val spacing: Dp,
) {
    companion object {
        internal val Pointer = TextButtonDimens(
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 5.dp),
            spacing = 8.dp,
        )

        internal val Touch = TextButtonDimens(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
            spacing = 12.dp,
        )

        fun of(interactType: InteractType): TextButtonDimens {
            return when (interactType) {
                InteractType.Pointer -> Pointer
                InteractType.Touch -> Touch
            }
        }
    }
}

object TextButtonDefaults {
    @Composable
    fun dimens(
        interactType: InteractType = LocalInteractType.current
    ): TextButtonDimens = TextButtonDimens.of(interactType)
}

@Composable
fun TextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = ContinuousRoundedCornerShape(20.dp),
    textStyle: TextStyle = LocalTextStyle.current,
    color: Color = ZithianTheme.colors.link,
    hoverTextStyle: TextStyle = textStyle.copy(textDecoration = TextDecoration.Underline),
    leading: (@Composable () -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null,
    dimens: TextButtonDimens = TextButtonDefaults.dimens(),
    content: @Composable () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val currentTextStyle = (if (isHovered) hoverTextStyle else textStyle).copy(color = color)
    val interactType = LocalInteractType.current

    Box(
        modifier = modifier
            .then(
                if (interactType == InteractType.Pointer) {
                    Modifier.pointerHoverIcon(PointerIcon.Hand)
                } else {
                    Modifier
                }
            )
            .hoverable(interactionSource)
            .clip(shape)
            .clickable(
                onClick = onClick,
                role = Role.Button,
                interactionSource = interactionSource,
                indication = null // Text buttons often handle their own state or rely on hover
            )
            .padding(dimens.contentPadding),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (leading != null) {
                leading()
                Spacer(modifier = Modifier.width(dimens.spacing))
            }
            CompositionLocalProvider(
                LocalTextStyle provides currentTextStyle
            ) {
                content()
            }
            if (trailing != null) {
                Spacer(modifier = Modifier.width(dimens.spacing))
                trailing()
            }
        }
    }
}

