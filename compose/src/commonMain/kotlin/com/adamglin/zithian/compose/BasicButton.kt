package com.adamglin.zithian.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
import com.adamglin.zithian.compose.theme.LocalContentColor
import com.adamglin.zithian.compose.utils.ifNotNull
import dev.chrisbanes.haze.HazeState
import io.github.fletchmckee.liquid.LiquidState
import io.github.fletchmckee.liquid.liquid

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
    contentPadding: PaddingValues = PaddingValues(23.dp, 12.dp),
    edgeInsetsInsteadPadding: Dp = 7.dp,
    enabled: Boolean = true,
    content: @Composable () -> Unit
) {
    Row(
        modifier = modifier
            .clip(shape)
            .clickable(enabled = enabled, onClick = onClick)
            .pointerHoverIcon(PointerIcon.Hand)
            .background(backgroundColor, shape)
            .then(
                if (liquidState != null) Modifier.liquid(liquidState) else Modifier
            )
            .ifNotNull(liquidState) { Modifier.liquid(it) }
            .padding(contentPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally)
    ) {
        CompositionLocalProvider(
            LocalContentColor provides foregroundColor,
            LocalTextStyle provides textStyle,
        ) {
            leading?.let {
                Box(modifier = Modifier.padding(start = edgeInsetsInsteadPadding).size(20.dp)) {
                    it()
                }
            }
            content()
            trailing?.let {
                Box(modifier = Modifier.padding(start = edgeInsetsInsteadPadding).size(20.dp)) {
                    it()
                }
            }
        }
    }
}