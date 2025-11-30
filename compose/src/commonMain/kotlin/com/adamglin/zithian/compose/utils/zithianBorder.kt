package com.adamglin.zithian.compose.utils

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.Dp

enum class BorderType {
    Outside, Inside, Center
}

fun Modifier.zithianBorder(
    width: Dp,
    color: Color,
    shape: Shape,
    type: BorderType = BorderType.Inside
) = drawWithCache {
    val strokeWidthPx = width.toPx()

    if (type == BorderType.Outside) {
        // Outside 模式：使用双倍宽度的 Stroke，并裁剪掉内部
        // 这种方式利用 Stroke 自然的外部扩展，保证了圆角的同心性，避免了手动计算 inset 导致的圆角错位
        val outline = shape.createOutline(size, layoutDirection, this)
        onDrawWithContent {
            drawContent()
            drawIntoCanvas { canvas ->
                canvas.save()
                
                // 裁剪掉内部 (Difference)，只保留外部区域
                when (outline) {
                    is Outline.Rectangle -> canvas.clipRect(outline.rect, ClipOp.Difference)
                    is Outline.Rounded -> canvas.clipPath(Path().apply { addRoundRect(outline.roundRect) }, ClipOp.Difference)
                    is Outline.Generic -> canvas.clipPath(outline.path, ClipOp.Difference)
                }
                
                // 绘制双倍宽度的 Stroke
                // Stroke 中心在边缘，宽度为 2*width，所以一半在内(被剪掉)，一半在外(保留)，看起来就是宽度为 width 的外部边框
                drawOutline(
                    outline = outline,
                    color = color,
                    style = Stroke(width = strokeWidthPx * 2)
                )
                
                canvas.restore()
            }
        }
    } else {
        val halfStroke = strokeWidthPx / 2f
        val inset = if (type == BorderType.Inside) halfStroke else 0f // Center 不偏移

        val insetSize = Size(
            size.width - 2 * inset,
            size.height - 2 * inset
        )

        // Prevent negative sizes (can happen with Inside type and large border)
        if (insetSize.width <= 0f || insetSize.height <= 0f) {
            onDrawWithContent {
                drawContent()
            }
        } else {
            val outline = shape.createOutline(
                insetSize,
                layoutDirection,
                this
            )

            onDrawWithContent {
                drawContent()
                withTransform({
                    translate(left = inset, top = inset)
                }) {
                    drawOutline(
                        outline = outline,
                        color = color,
                        style = Stroke(width = strokeWidthPx)
                    )
                }
            }
        }
    }
}

fun Modifier.innerBorder(
    width: Dp,
    color: Color,
    shape: Shape
) = zithianBorder(width, color, shape, BorderType.Inside)

fun Modifier.outerBorder(
    width: Dp,
    color: Color,
    shape: Shape
) = zithianBorder(width, color, shape, BorderType.Outside)

fun Modifier.shadowBorderWithHover(shape: Shape) = composed {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val colorAlphaOffset by animateFloatAsState(
        if (isHovered) 0.02f else 0f,
    )
    this.hoverable(interactionSource)
        .dropShadow(shape) {
            offset = Offset(0f, 0f)
            radius = 0f
            spread = 1f
            color = Color.Black.copy((0.06f + colorAlphaOffset))
        }
        .dropShadow(shape) {
            offset = Offset(0f, 1f)
            radius = 2f
            spread = -1f
            color = Color.Black.copy((0.06f + colorAlphaOffset) )
        }
        .dropShadow(shape) {
            offset = Offset(0f, 2f)
            radius = 4f
            spread = 0f
            color = Color.Black.copy((0.04f + colorAlphaOffset))
        }
}
