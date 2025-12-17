package com.adamglin.zithian.compose.radio

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.adamglin.zithian.compose.theme.InteractType
import com.adamglin.zithian.compose.theme.LocalInteractType
import com.adamglin.zithian.compose.theme.ZithianTheme
import com.adamglin.zithian.compose.utils.ifTrue
import com.adamglin.zithian.compose.utils.interactPointer

@Immutable
data class RadioDimens(
    val size: Dp,
    val dotSize: Dp,
    val contentPadding: PaddingValues,
) {
    companion object {
        internal val Pointer = RadioDimens(
            size = 18.dp,
            dotSize = 8.dp,
            contentPadding = PaddingValues(1.dp),
        )
        internal val Touch = RadioDimens(
            size = 24.dp,
            dotSize = 12.dp,
            contentPadding = PaddingValues(1.dp),
        )

        fun of(interactType: InteractType) = when (interactType) {
            InteractType.Pointer -> Pointer
            InteractType.Touch -> Touch
        }
    }
}

@Immutable
data class RadioColors(
    val uncheckedBackgroundColor: Color,
    val uncheckedForegroundColor: Color,
    val checkedBackgroundColor: Color,
    val checkedForegroundColor: Color
)

object RadioDefaults {
    @Composable
    fun dimens(interactType: InteractType = LocalInteractType.current) = RadioDimens.of(interactType)

    @Composable
    fun colors() = RadioColors(
        uncheckedBackgroundColor = ZithianTheme.colors.text11,
        uncheckedForegroundColor = Color.Transparent,
        checkedBackgroundColor = ZithianTheme.colors.primary,
        checkedForegroundColor = ZithianTheme.colors.onPrimary
    )
}

@Composable
fun Radio(
    selected: Boolean,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    dimens: RadioDimens = RadioDefaults.dimens(),
    colors: RadioColors = RadioDefaults.colors()
) {
    val interactType = LocalInteractType.current
    val isHovered by interactionSource.collectIsHoveredAsState()

    val backgroundColor = when {
        interactType == InteractType.Pointer && !selected && isHovered -> colors.checkedBackgroundColor.copy(.2f)
        interactType == InteractType.Pointer && selected && isHovered -> colors.checkedBackgroundColor.copy(.8f)
        selected -> colors.checkedBackgroundColor
        !selected -> colors.uncheckedBackgroundColor
        else -> Color.Transparent
    }

    Box(
        modifier = modifier
            .size(dimens.size)
            .interactPointer(
                interactType = interactType,
                enabled = enabled
            )
            .clip(CircleShape)
            .background(backgroundColor, CircleShape)
            .hoverable(interactionSource)
            .ifTrue(enabled && onClick != null) {
                Modifier.clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    role = Role.RadioButton,
                    onClick = onClick!!
                )
            }
            .ifTrue(!enabled) {
                Modifier.alpha(0.3f)
            },
        contentAlignment = Alignment.Center
    ) {
        if (selected) {
            Box(
                modifier = Modifier
                    .size(dimens.dotSize)
                    .clip(CircleShape)
                    .background(colors.checkedForegroundColor)
            )
        }
    }
}






