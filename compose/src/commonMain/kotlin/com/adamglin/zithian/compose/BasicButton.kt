package com.adamglin.zithian.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.adamglin.composecontinuousroundedcornershape.ContinuousRoundedCornerShape
import com.adamglin.zithian.compose.theme.InteractType
import com.adamglin.zithian.compose.theme.LocalContentColor
import com.adamglin.zithian.compose.theme.LocalInteractType
import com.adamglin.zithian.compose.utils.ifNotNull
import dev.chrisbanes.haze.HazeState
import io.github.fletchmckee.liquid.LiquidState
import io.github.fletchmckee.liquid.liquid

@Immutable
data class BasicButtonDimens(
    val contentPadding: PaddingValues,
    val iconSpacing: Dp,
    val iconSize: Dp,
) {
    companion object {
        internal val Pointer = BasicButtonDimens(
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
            iconSpacing = 6.dp,
            iconSize = 17.dp,
        )

        internal val Touch = BasicButtonDimens(
            contentPadding = PaddingValues(horizontal = 23.dp, vertical = 12.dp),
            iconSpacing = 12.dp,
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
    liquidState: LiquidState? = null,
    hazeState: HazeState? = null,
    shape: Shape = ContinuousRoundedCornerShape(20.dp),
    backgroundColor: Color = Color.Black,
    foregroundColor: Color = Color.White,
    textStyle: TextStyle = LocalTextStyle.current,
    leading: (@Composable () -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null,
    dimens: BasicButtonDimens = BasicButtonDefaults.dimens(),
    enabled: Boolean = true,
    content: @Composable () -> Unit
) {
    val interactType = LocalInteractType.current
    
    Row(
        modifier = modifier
            .clip(shape)
            .clickable(enabled = enabled, onClick = onClick)
            .then(
                if (interactType == InteractType.Pointer) {
                    Modifier.pointerHoverIcon(PointerIcon.Hand)
                } else {
                    Modifier
                }
            )
            .background(backgroundColor, shape)
            .then(
                if (liquidState != null) Modifier.liquid(liquidState) else Modifier
            )
            .ifNotNull(liquidState) { Modifier.liquid(it) }
            .padding(dimens.contentPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimens.iconSpacing, Alignment.CenterHorizontally)
    ) {
        CompositionLocalProvider(
            LocalContentColor provides foregroundColor,
            LocalTextStyle provides textStyle,
        ) {
            leading?.let {
                Box(modifier = Modifier.size(dimens.iconSize)) {
                    it()
                }
            }
            content()
            trailing?.let {
                Box(modifier = Modifier.size(dimens.iconSize)) {
                    it()
                }
            }
        }
    }
}
