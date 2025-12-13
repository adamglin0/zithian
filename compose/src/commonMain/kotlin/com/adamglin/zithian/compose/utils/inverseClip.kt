package com.adamglin.zithian.compose.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas

fun Modifier.inverseClip(shape: Shape): Modifier = this
    .graphicsLayer {
        compositingStrategy = CompositingStrategy.Offscreen
    }
    .drawWithContent {
        drawContent()

        drawIntoCanvas { canvas ->
            val paint = Paint().apply {
                blendMode = BlendMode.Clear
                isAntiAlias = true
            }

            val outline = shape.createOutline(size, layoutDirection, this)

            canvas.drawOutline(outline, paint)
        }
    }
