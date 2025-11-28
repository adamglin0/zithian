package com.adamglin.zithian.compose.indication

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.node.DrawModifierNode
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

internal object ZithianScaleIndication : IndicationNodeFactory {
    override fun create(interactionSource: InteractionSource): DelegatableNode =
        ScaleIndicationNode(interactionSource)

    override fun hashCode(): Int = -1

    override fun equals(other: Any?) = other === this

    private class ScaleIndicationNode(
        private val interactionSource: InteractionSource
    ) : Modifier.Node(), DrawModifierNode {
        private val animatedScalePercent = Animatable(1f)

        private suspend fun animateToPressed() {
            animatedScalePercent.animateTo(0.96f, spring())
        }

        private suspend fun animateToResting() {
            animatedScalePercent.animateTo(1f, tween())
        }

        override fun onAttach() {
            coroutineScope.launch {
                launch {
                    interactionSource.interactions.collectLatest { interaction ->
                        when (interaction) {
                            is PressInteraction.Press -> animateToPressed()
                            is HoverInteraction.Exit,
                            is PressInteraction.Release,
                            is PressInteraction.Cancel -> animateToResting()
                        }
                    }
                }
            }
        }

        override fun ContentDrawScope.draw() {
            scale(
                scale = animatedScalePercent.value
            ) {
                this@draw.drawContent()
            }
        }
    }
}