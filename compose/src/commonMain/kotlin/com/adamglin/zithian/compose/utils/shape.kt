package com.adamglin.zithian.compose.utils

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import kotlin.math.*

/**
 * 高性能连续圆角矩形（iOS 风格）
 *
 * - curvature = 0f 表示普通圆角
 * - curvature = 0.6f 表示 iOS 连续圆角（推荐）
 * - curvature = 1f 表示超柔顺（夸张曲率）
 *
 * 注意：为保证嵌套圆角的同心性，scaleFactor 针对每个 curvature 值预计算为常量
 */
class ContinuousRoundedCornerShape0(
    private val corner: Dp,
    private val curvature: Float = 0.6f
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val width = size.width
        val height = size.height
        if (width <= 0f || height <= 0f) {
            return Outline.Rectangle(Rect(0f, 0f, width, height))
        }

        val cornerPx = with(density) { corner.toPx().coerceAtLeast(0f) }
        if (cornerPx == 0f) {
            return Outline.Rectangle(Rect(0f, 0f, width, height))
        }

        val smooth = curvature.coerceIn(0f, 1f)
        if (smooth <= 1e-4f) {
            val r = min(cornerPx, min(width, height) / 2f)
            return Outline.Rounded(RoundRect(0f, 0f, width, height, CornerRadius(r)))
        }

        // superellipse 曲率 n（2=圆，越大越接近方形，但边缘更平滑）
        val n = 2f + smooth * 4f
        val powExp = 2f / n

        // ✅ 使用预计算的 scaleFactor 常量，保证相同 curvature 下的线性关系
        // 这样嵌套圆角（如 border）可以保持同心
        val scaleFactor = getScaleFactor(smooth, powExp)
        val r = (cornerPx * scaleFactor).coerceAtMost(min(width, height) / 2f)

        // 步数按 r 大小自适应，减少 Path 点数
        val steps = when {
            r < 12f -> 6
            r < 24f -> 8
            r < 48f -> 12
            else -> 16
        }

        val path = Path()
        val quarter = (PI / 2).toFloat()

        // 快速生成一个 superellipse 1/4 边的坐标缓存
        val hv = FloatArray((steps + 1) * 2)
        val vh = FloatArray((steps + 1) * 2)

        for (i in 0..steps) {
            val t = i * quarter / steps
            val sinT = sin(t.toDouble()).toFloat().absoluteValue
            val cosT = cos(t.toDouble()).toFloat().absoluteValue
            val powS = sinT.pow(powExp)
            val powC = cosT.pow(powExp)
            hv[i * 2] = (r * powS)
            hv[i * 2 + 1] = (r * (1f - powC))
            vh[i * 2] = (r * (1f - powC))
            vh[i * 2 + 1] = (r * powS)
        }

        // 起点（TopStart 角）
        path.moveTo(r, 0f)

        // Top edge
        path.lineTo(width - r, 0f)
        // TopEnd corner（右上）
        for (i in 1..steps) {
            val xh = hv[i * 2]
            val yh = hv[i * 2 + 1]
            path.lineTo(width - r + xh, yh)
        }

        // Right edge
        path.lineTo(width, height - r)
        // BottomEnd corner（右下）
        for (i in 1..steps) {
            val xv = vh[i * 2]
            val yv = vh[i * 2 + 1]
            path.lineTo(width - xv, height - r + yv)
        }

        // Bottom edge
        path.lineTo(r, height)
        // BottomStart corner（左下）
        for (i in 1..steps) {
            val xh = hv[i * 2]
            val yh = hv[i * 2 + 1]
            path.lineTo(r - xh, height - yh)
        }

        // Left edge
        path.lineTo(0f, r)
        // TopStart corner（左上）
        for (i in 1..steps) {
            val xv = vh[i * 2]
            val yv = vh[i * 2 + 1]
            path.lineTo(xv, r - yv)
        }

        path.close()

        return Outline.Generic(path)
    }

    companion object {
        // 缓存 scaleFactor，避免重复计算（Key 是 smooth 值）
        private val scaleFactorCache = mutableMapOf<Float, Float>()

        /**
         * 计算视觉校准因子，使 Superellipse 的视觉大小与标准圆角一致
         *
         * 关键：对于相同的 curvature，始终返回相同的 scaleFactor
         * 这保证了嵌套圆角的同心性（半径差 = corner 差）
         */
        private fun getScaleFactor(smooth: Float, powExp: Float): Float {
            // 使用缓存避免重复计算
            return scaleFactorCache.getOrPut(smooth) {
                val cos45 = 0.70710678f
                val yCircle = 1f - cos45
                val ySuper = 1f - cos45.pow(powExp)
                if (ySuper > 0f) yCircle / ySuper else 1f
            }
        }
    }
}