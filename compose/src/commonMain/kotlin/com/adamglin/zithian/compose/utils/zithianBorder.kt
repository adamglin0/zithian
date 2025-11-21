package com.adamglin.zithian.compose.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.Dp

fun Modifier.zithianBorder(
    width: Dp,
    color: Color,
    shape: Shape
) = drawBehind {
    val strokeWidthPx = width.toPx()

    // ⬇️ 创建 "内缩" 的形状，以保证边框完全画在内部
    val insetSize = Size(
        size.width - strokeWidthPx,
        size.height - strokeWidthPx
    )

    // 注意：如果宽或高变成负值（极端情况），直接返回
    if (insetSize.width <= 0f || insetSize.height <= 0f) return@drawBehind

    val outline = shape.createOutline(
        insetSize,
        layoutDirection,
        this
    )

    // ⬇️ 将路径平移 strokeWidth/2，使其回到视觉中心
    val translate = strokeWidthPx / 2

    if (outline is Outline.Generic) {
        withTransform({
            translate(left = translate, top = translate)
        }) {
            drawPath(
                outline.path,
                color = color,
                style = Stroke(width = strokeWidthPx)
            )
        }
    }
}