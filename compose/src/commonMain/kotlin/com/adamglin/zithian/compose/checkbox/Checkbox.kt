package com.adamglin.zithian.compose.checkbox

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.adamglin.composecontinuousroundedcornershape.ContinuousRoundedCornerShape
import com.adamglin.zithian.compose.generated.resources.ZithianResources
import com.adamglin.zithian.compose.icon.CoilIcon
import com.adamglin.zithian.compose.theme.InteractType
import com.adamglin.zithian.compose.theme.LocalInteractType
import com.adamglin.zithian.compose.theme.ZithianTheme
import com.adamglin.zithian.compose.utils.ifTrue
import com.adamglin.zithian.compose.utils.interactPointer

@Immutable
data class CheckboxDimens(
    val size: Dp,
    val cornerRadius: Dp,
    val contentPadding: PaddingValues,
) {
    companion object {
        internal val Pointer = CheckboxDimens(
            size = 18.dp,
            cornerRadius = 4.dp,
            contentPadding = PaddingValues(1.dp),
        )
        internal val Touch = CheckboxDimens(
            size = 24.dp,
            cornerRadius = 8.dp,
            contentPadding = PaddingValues(1.dp),
        )

        fun of(interactType: InteractType) = when (interactType) {
            InteractType.Pointer -> Pointer
            InteractType.Touch -> Touch
        }
    }
}

@Immutable
data class CheckboxColors(
    val uncheckedBackgroundColor: Color,
    val uncheckedForegroundColor: Color,
    val checkedBackgroundColor: Color,
    val checkedForegroundColor: Color
)

object CheckboxDefaults {
    @Composable
    fun dimens(interactType: InteractType = LocalInteractType.current) = CheckboxDimens.of(interactType)

    @Composable
    fun colors() = CheckboxColors(
        uncheckedBackgroundColor = ZithianTheme.colors.text11,
        uncheckedForegroundColor = Color.Transparent,
        checkedBackgroundColor = ZithianTheme.colors.primary,
        checkedForegroundColor = ZithianTheme.colors.onPrimary
    )
}

@Composable
fun Checkbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    dimens: CheckboxDimens = CheckboxDefaults.dimens(),
    colors: CheckboxColors = CheckboxDefaults.colors()
) {
    val internalShape = ContinuousRoundedCornerShape(dimens.cornerRadius)
    val interactType = LocalInteractType.current
    val isHovered by interactionSource.collectIsHoveredAsState()

    val backgroundColor = when {
        interactType == InteractType.Pointer && !checked && isHovered -> colors.checkedBackgroundColor.copy(.2f)
        interactType == InteractType.Pointer && checked && isHovered -> colors.checkedBackgroundColor.copy(.8f)
        checked -> colors.checkedBackgroundColor
        !checked -> colors.uncheckedBackgroundColor
        else -> Color.Transparent
    }
    val foregroundColor = if (checked) colors.checkedForegroundColor else colors.uncheckedForegroundColor

    Box(
        modifier = modifier
            .size(dimens.size)
            .interactPointer(
                interactType = interactType,
                enabled = enabled
            )
            .clip(internalShape)
            .background(backgroundColor, internalShape)
            .hoverable(interactionSource)
            .ifTrue(enabled) {
                Modifier.clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    role = Role.Checkbox
                ) { onCheckedChange(!checked) }
            }
            .ifTrue(!enabled) {
                Modifier.alpha(0.3f)
            },
        contentAlignment = Alignment.Center
    ) {
        if (checked) {
            CoilIcon(
                uri = ZithianResources.getUri("drawable/ic_check.svg"),
                contentDescription = null,
                tint = foregroundColor,
                modifier = Modifier.padding(dimens.contentPadding)
            )
        }
    }
}

