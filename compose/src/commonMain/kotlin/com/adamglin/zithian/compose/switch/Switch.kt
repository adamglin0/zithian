package com.adamglin.zithian.compose.switch

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.adamglin.composecontinuousroundedcornershape.ContinuousRoundedCornerShape
import com.adamglin.zithian.compose.theme.InteractType
import com.adamglin.zithian.compose.theme.LocalInteractType
import com.adamglin.zithian.compose.theme.ZithianTheme
import com.adamglin.zithian.compose.utils.ifTrue

@Immutable
data class SwitchDimens(
    val dotSize: Dp,
    val contentPadding: Dp,
) {
    companion object {
        internal val Pointer = SwitchDimens(
            dotSize = 15.dp,
            contentPadding = 2.dp,
        )

        internal val Touch = SwitchDimens(
            dotSize = 21.dp,
            contentPadding = 3.5.dp,
        )

        fun of(interactType: InteractType): SwitchDimens {
            return when (interactType) {
                InteractType.Pointer -> Pointer
                InteractType.Touch -> Touch
            }
        }
    }
}

object SwitchDefaults {
    @Composable
    fun dimens(
        interactType: InteractType = LocalInteractType.current
    ): SwitchDimens = SwitchDimens.of(interactType)
}

@Composable
fun SmallSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) = Switch(
    checked,
    onCheckedChange,
    modifier,
    enabled,
    interactionSource,
    dimens = SwitchDefaults.dimens(InteractType.Pointer)
)

@Composable
fun Switch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    dimens: SwitchDimens = SwitchDefaults.dimens(),
) {
    val interactType = LocalInteractType.current
    val dotSize = dimens.dotSize
    val contentPadding = dimens.contentPadding

    val dotColor by animateColorAsState(
        if (checked) ZithianTheme.colors.background else ZithianTheme.colors.surface
    )

    val backgroundColor by animateColorAsState(
        if (checked) ZithianTheme.colors.primary else ZithianTheme.colors.background,
    )

    val offsetX by animateDpAsState(
        if (checked) dotSize - contentPadding else 0.dp
    )

    Row(
        modifier = modifier
            .clip(ContinuousRoundedCornerShape(100.dp))
            .hoverable(interactionSource)
            .width(dotSize * 2 + contentPadding).background(backgroundColor, ContinuousRoundedCornerShape(100f))
            .ifTrue(enabled) {
                Modifier.clickable(role = Role.Switch) { onCheckedChange(!checked) }
            }
            .ifTrue(!enabled) {
                Modifier.alpha(.3f)
            }
    ) {
        Box(
            modifier = Modifier
                .dropShadow(
                    CircleShape, Shadow(
                        radius = 10.dp,
                        color = ZithianTheme.colors.shadow,
                    )
                )
                .offset(x = offsetX)
                .padding(contentPadding)
                .size(dotSize)
                .background(dotColor, CircleShape)
        )
    }
}
