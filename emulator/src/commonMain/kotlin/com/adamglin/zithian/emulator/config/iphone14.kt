@file:OptIn(InternalComposeUiApi::class)

package com.adamglin.zithian.emulator.config

import androidx.compose.ui.InternalComposeUiApi
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.PlatformInsets
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.adamglin.zithian.emulator.Device

@OptIn(InternalComposeUiApi::class)
fun Device.Companion.iphone14(density: Density) = Iphone14DeviceParameters(density)

class Iphone14DeviceParameters(override val density: Density) : Device {
    override val name: String
        get() = "iPhone 14"

    override val statusBars: PlatformInsets
        get() = with(density) { PlatformInsets(top = 47.dp.roundToPx()) }

    override val navigationBars: PlatformInsets
        get() = with(density) { PlatformInsets(bottom = 34.dp.roundToPx()) }

    override val systemBars: PlatformInsets
        get() = with(density) { PlatformInsets(top = 47.dp.roundToPx(), bottom = 34.dp.roundToPx()) }

    override val captionBar: PlatformInsets
        get() = PlatformInsets(top = 0)

    override val displayCutout: PlatformInsets
        get() = with(density) { PlatformInsets(top = 47.dp.roundToPx()) }

    override val displayCutouts: List<Rect>
        get() = with(density) {
            // Notch: approximately 209dp wide, 30dp tall, centered at top
            val notchWidth = 209.dp.toPx()
            val notchHeight = 30.dp.toPx()
            val screenWidth = width.toPx()
            val left = (screenWidth - notchWidth) / 2
            val right = left + notchWidth
            listOf(Rect(left, 0f, right, notchHeight))
        }

    override val cutoutPath: Path
        get() = with(density) {
            // iPhone 14 notch shape - a cutout from the top edge with curved transitions
            val notchWidth = 162.dp.toPx()
            val notchHeight = 32.dp.toPx()
            val screenWidth = width.toPx()
            val notchLeft = (screenWidth - notchWidth) / 2
            val notchRight = notchLeft + notchWidth
            val curveRadius = 20.dp.toPx()
            val smallCurveRadius = 18.dp.toPx()

            Path().apply {
                // Start from top-left corner of screen
                moveTo(0f, 0f)
                // Line to left side of notch transition
                lineTo(notchLeft - curveRadius, 0f)
                // Curve down into notch (left side)
                quadraticTo(
                    notchLeft, 0f,
                    notchLeft, curveRadius
                )
                // Line down to bottom-left of notch
                lineTo(notchLeft, notchHeight - smallCurveRadius)
                // Small curve at bottom-left of notch
                quadraticTo(
                    notchLeft, notchHeight,
                    notchLeft + smallCurveRadius, notchHeight
                )
                // Line across bottom of notch
                lineTo(notchRight - smallCurveRadius, notchHeight)
                // Small curve at bottom-right of notch
                quadraticTo(
                    notchRight, notchHeight,
                    notchRight, notchHeight - smallCurveRadius
                )
                // Line up to top-right of notch
                lineTo(notchRight, curveRadius)
                // Curve up out of notch (right side)
                quadraticTo(
                    notchRight, 0f,
                    notchRight + curveRadius, 0f
                )
                // Line to top-right corner of screen
                lineTo(screenWidth, 0f)
                // Close the path along the top edge
                close()
            }
        }

    override val ime: PlatformInsets
        get() = PlatformInsets(bottom = 0)

    override val mandatorySystemGestures: PlatformInsets
        get() = with(density) { PlatformInsets(bottom = 34.dp.roundToPx()) }

    override val systemGestures: PlatformInsets
        get() = with(density) {
            PlatformInsets(
                left = 20.dp.roundToPx(),
                right = 20.dp.roundToPx(),
                bottom = 34.dp.roundToPx()
            )
        }

    override val tappableElement: PlatformInsets
        get() = with(density) { PlatformInsets(top = 47.dp.roundToPx()) }

    override val waterfall: PlatformInsets
        get() = PlatformInsets(0, 0, 0, 0)

    override val width: Dp
        get() = 390.dp

    override val height: Dp
        get() = 844.dp

    override val roundedCornerSize: Dp
        get() = 47.dp
}

