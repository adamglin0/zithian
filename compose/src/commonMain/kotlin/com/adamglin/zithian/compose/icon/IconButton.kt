package com.adamglin.zithian.compose.icon

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.adamglin.composecontinuousroundedcornershape.ContinuousRoundedCornerShape
import com.adamglin.zithian.compose.theme.InteractType
import com.adamglin.zithian.compose.theme.LocalContentColor
import com.adamglin.zithian.compose.theme.LocalInteractType
import com.adamglin.zithian.compose.utils.ifNotNull
import com.adamglin.zithian.compose.utils.ifTrue
import com.adamglin.zithian.compose.utils.interactPointer
import io.github.fletchmckee.liquid.LiquidState
import io.github.fletchmckee.liquid.liquid

@Immutable
data class IconButtonDimens(
    val contentPadding: PaddingValues,
    val cornerRadius: Dp,
    val iconSize: Dp,
) {
    companion object {
        internal val Pointer = IconButtonDimens(
            contentPadding = PaddingValues(10.dp),
            cornerRadius = 100.dp,
            iconSize = 17.dp,
        )

        internal val Touch = IconButtonDimens(
            contentPadding = PaddingValues(10.dp),
            cornerRadius = 100.dp,
            iconSize = 24.dp,
        )

        fun of(interactType: InteractType): IconButtonDimens {
            return when (interactType) {
                InteractType.Pointer -> Pointer
                InteractType.Touch -> Touch
            }
        }
    }
}

@Immutable
data class IconButtonColors(
    val backgroundColor: Color,
    val foregroundColor: Color,
    val pressedBackgroundColor: Color,
    val pressedForegroundColor: Color
)

internal object IconButtonDefaults {
    @Composable
    fun dimens(
        interactType: InteractType = LocalInteractType.current
    ): IconButtonDimens = IconButtonDimens.of(interactType)

    @Composable
    fun colors(
        backgroundColor: Color = Color.Transparent,
        foregroundColor: Color = Color.Unspecified,
        pressedBackgroundColor: Color = Color.Transparent,
        pressedForegroundColor: Color = foregroundColor
    ) = IconButtonColors(
        backgroundColor = backgroundColor,
        foregroundColor = foregroundColor,
        pressedBackgroundColor = pressedBackgroundColor,
        pressedForegroundColor = pressedForegroundColor
    )
}

@Composable
internal fun IconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    dimens: IconButtonDimens = IconButtonDefaults.dimens(),
    colors: IconButtonColors = IconButtonDefaults.colors(),
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    liquidState: LiquidState? = null,
    content: @Composable () -> Unit,
) {
    val interactType = LocalInteractType.current
    val isPressed by interactionSource.collectIsPressedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()
    val internalBackgroundColor = if (isPressed) colors.pressedBackgroundColor else colors.backgroundColor
    val internalForegroundColor = if (isPressed) colors.pressedForegroundColor else colors.foregroundColor
    val shape = ContinuousRoundedCornerShape(dimens.cornerRadius)

    Box(
        modifier = modifier
            .alpha(if (enabled) 1f else .4f)
            .ifTrue(enabled && isHovered) {
                Modifier.alpha(0.95f)
            }
            .clickable(
                enabled = enabled,
                role = Role.Button,
                onClick = onClick,
                interactionSource = interactionSource,
            )
            .interactPointer(interactType, enabled)
            .ifNotNull(liquidState) {
                Modifier.liquid(it) {
                    this.shape = RoundedCornerShape(dimens.cornerRadius)
                    tint = if (colors.backgroundColor == Color.Transparent) Color.Transparent
                    else colors.backgroundColor.copy(.8f)
                    edge = 0.02f
                }
            }
            .ifTrue(liquidState == null) {
                Modifier.background(colors.backgroundColor, shape)
            }
            .padding(dimens.contentPadding),
        contentAlignment = Alignment.Center
    ) {
        CompositionLocalProvider(LocalContentColor provides internalForegroundColor) {
            Box(modifier = Modifier.size(dimens.iconSize)) {
                content()
            }
        }
    }
}
