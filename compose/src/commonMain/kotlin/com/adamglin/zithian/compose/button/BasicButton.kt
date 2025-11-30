package com.adamglin.zithian.compose.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.adamglin.composecontinuousroundedcornershape.ContinuousRoundedCornerShape
import com.adamglin.zithian.compose.text.LocalTextStyle
import com.adamglin.zithian.compose.theme.InteractType
import com.adamglin.zithian.compose.theme.LocalContentColor
import com.adamglin.zithian.compose.theme.LocalInteractType
import com.adamglin.zithian.compose.theme.ZithianTheme
import com.adamglin.zithian.compose.utils.BorderType
import com.adamglin.zithian.compose.utils.ifTrue
import com.adamglin.zithian.compose.utils.interactPointer
import com.adamglin.zithian.compose.utils.innerBorder
import com.adamglin.zithian.compose.utils.outerBorder
import com.adamglin.zithian.compose.utils.zithianBorder

@Immutable
data class BasicButtonDimens(
    val contentPadding: PaddingValues,
    val cornerRadius: Dp,
    val iconSpacing: Dp,
    val iconSize: Dp,
) {
    companion object {
        internal val Pointer = BasicButtonDimens(
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
            iconSpacing = 6.dp,
            cornerRadius = 10.dp,
            iconSize = 17.dp,
        )

        internal val Touch = BasicButtonDimens(
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
            iconSpacing = 12.dp,
            cornerRadius = 20.dp,
            iconSize = 24.dp,
        )

        fun of(interactType: InteractType): BasicButtonDimens {
            return when (interactType) {
                InteractType.Pointer -> Pointer
                InteractType.Touch -> Touch
            }
        }
    }
}

object BasicButtonDefaults {
    @Composable
    fun dimens(
        interactType: InteractType = LocalInteractType.current
    ): BasicButtonDimens = BasicButtonDimens.of(interactType)
}

@Composable
fun BasicButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    dimens: BasicButtonDimens = BasicButtonDefaults.dimens(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    backgroundColor: Color = Color.Black,
    foregroundColor: Color = Color.White,
    textStyle: TextStyle = LocalTextStyle.current,
    leading: (@Composable () -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
    content: @Composable () -> Unit
) {
    val interactType = LocalInteractType.current
    val shape = remember(dimens.cornerRadius) { ContinuousRoundedCornerShape(dimens.cornerRadius) }
    val isPressed by interactionSource.collectIsPressedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isFocused by interactionSource.collectIsFocusedAsState()
    Row(
        modifier = modifier
            .clickable(
                enabled = enabled,
                onClick = onClick,
                interactionSource = interactionSource,
                role = Role.Button
            )
            .ifTrue(
                value = { isFocused }
            ) { Modifier.zithianBorder(4.dp, ZithianTheme.colors.focusColor, shape, BorderType.Outside) }
            .interactPointer(interactType)
            .background(backgroundColor, shape)
            .padding(dimens.contentPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimens.iconSpacing, Alignment.CenterHorizontally)
    ) {
        CompositionLocalProvider(
            LocalContentColor provides foregroundColor,
            LocalTextStyle provides textStyle,
        ) {
            if (leading != null) {
                Box(modifier = Modifier.size(dimens.iconSize)) {
                    leading()
                }
            }
            content()
            if (trailing != null) {
                Box(modifier = Modifier.size(dimens.iconSize)) {
                    trailing()
                }
            }
        }
    }
}

