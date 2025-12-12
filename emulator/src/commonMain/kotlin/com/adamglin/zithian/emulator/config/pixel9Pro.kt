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
fun Device.Companion.pixel9Pro(density: Density) = Pixel9ProDeviceParameters(density)

class Pixel9ProDeviceParameters(override val density: Density) : Device {
    override val name: String
        get() = "Pixel 9 Pro"

    override val statusBars: PlatformInsets
        get() = with(density) { PlatformInsets(top = 52.dp.roundToPx()) }

    override val navigationBars: PlatformInsets
        get() = with(density) { PlatformInsets(bottom = 24.dp.roundToPx()) }

    override val systemBars: PlatformInsets
        get() = with(density) { PlatformInsets(top = 52.dp.roundToPx(), bottom = 24.dp.roundToPx()) }

    override val captionBar: PlatformInsets
        get() = PlatformInsets(top = 0)

    override val displayCutout: PlatformInsets
        get() = with(density) { PlatformInsets(top = 52.dp.roundToPx()) }

    override val displayCutouts: List<Rect>
        get() = with(density) {
            // Punch-hole camera: approximately 28dp diameter, centered at top
            val holeRadius = 14.dp.toPx()
            val screenWidth = width.toPx()
            val centerX = screenWidth / 2
            val centerY = 26.dp.toPx() // Centered vertically in status bar area
            listOf(
                Rect(
                    centerX - holeRadius,
                    centerY - holeRadius,
                    centerX + holeRadius,
                    centerY + holeRadius
                )
            )
        }

    override val cutoutPath: Path
        get() = with(density) {
            // Punch-hole camera circle
            val holeRadius = 14.dp.toPx()
            val screenWidth = width.toPx()
            val centerX = screenWidth / 2
            val centerY = 26.dp.toPx()
            Path().apply {
                addOval(
                    Rect(
                        centerX - holeRadius,
                        centerY - holeRadius,
                        centerX + holeRadius,
                        centerY + holeRadius
                    )
                )
            }
        }

    override val ime: PlatformInsets
        get() = PlatformInsets(bottom = 0)

    override val mandatorySystemGestures: PlatformInsets
        get() = with(density) { PlatformInsets(bottom = 24.dp.roundToPx()) }

    override val systemGestures: PlatformInsets
        get() = with(density) {
            PlatformInsets(
                left = 24.dp.roundToPx(),
                right = 24.dp.roundToPx(),
                bottom = 24.dp.roundToPx()
            )
        }

    override val tappableElement: PlatformInsets
        get() = with(density) { PlatformInsets(top = 52.dp.roundToPx()) }

    override val waterfall: PlatformInsets
        get() = PlatformInsets(0, 0, 0, 0)

    override val width: Dp
        get() = 412.dp

    override val height: Dp
        get() = 915.dp

    override val roundedCornerSize: Dp
        get() = 32.dp
}

