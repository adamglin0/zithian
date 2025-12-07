package com.adamglin.zithian.compose.indication

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.node.LayoutModifierNode
import androidx.compose.ui.node.invalidatePlacement
import androidx.compose.ui.unit.Constraints
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

internal object ZithianScaleIndication : IndicationNodeFactory {
    override fun create(interactionSource: InteractionSource): DelegatableNode =
        ScaleIndicationNode(interactionSource)

    override fun hashCode(): Int = -1

    override fun equals(other: Any?) = other === this

    private class ScaleIndicationNode(
        private val interactionSource: InteractionSource
    ) : Modifier.Node(), LayoutModifierNode {
        private val animatedScalePercent = Animatable(1f)

        private suspend fun animateToPressed() {
            animatedScalePercent.animateTo(0.95f, spring()) {
                invalidatePlacement()
            }
        }

        private suspend fun animateToResting() {
            animatedScalePercent.animateTo(1f, tween()) {
                invalidatePlacement()
            }
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

        override fun MeasureScope.measure(measurable: Measurable, constraints: Constraints): MeasureResult {
            val placeable = measurable.measure(constraints)
            return layout(placeable.width, placeable.height) {
                placeable.placeWithLayer(0, 0) {
                    scaleX = animatedScalePercent.value
                    scaleY = animatedScalePercent.value
                }
            }
        }
    }
}