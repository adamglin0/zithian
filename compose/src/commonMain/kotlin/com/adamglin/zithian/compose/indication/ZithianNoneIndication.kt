package com.adamglin.zithian.compose.indication

import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.unit.dp
import com.adamglin.zithian.compose.theme.ZithianColors
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

internal class ZithianNoneIndication(
    private val colors: ZithianColors,
) : IndicationNodeFactory {
    override fun create(interactionSource: InteractionSource): DelegatableNode =
        NoneIndicationNode(interactionSource, colors.focusColor)

    override fun hashCode(): Int = -1

    override fun equals(other: Any?) = other === this

    private class NoneIndicationNode(
        private val interactionSource: InteractionSource,
        private val color: Color,
    ) : Modifier.Node(), DrawModifierNode {
        var isFocused by mutableStateOf(false)
        override fun ContentDrawScope.draw() {
            drawContent()
            if (isFocused) {
                drawRect(
                    color = color,
                    style = Stroke(width = 3.dp.toPx())
                )
            }
        }

        override fun onAttach() {
            coroutineScope.launch {
                launch {
                    interactionSource.interactions.collectLatest { interaction ->
                        when (interaction) {
                            is FocusInteraction.Focus -> isFocused = true
                            is FocusInteraction.Unfocus -> isFocused = false
                        }
                    }
                }
            }
        }
    }
}