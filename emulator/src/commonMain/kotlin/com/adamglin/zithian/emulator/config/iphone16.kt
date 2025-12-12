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
fun Device.Companion.iphone16(density: Density) = Iphone16DeviceParameters(density)

class Iphone16DeviceParameters(override val density: Density) : Device {
    override val name: String
        get() = "iPhone 16"

    override val statusBars: PlatformInsets
        get() = with(density) { PlatformInsets(top = 59.dp.roundToPx()) }

    override val navigationBars: PlatformInsets
        get() = with(density) { PlatformInsets(bottom = 34.dp.roundToPx()) }

    override val systemBars: PlatformInsets
        get() = with(density) { PlatformInsets(top = 59.dp.roundToPx(), bottom = 34.dp.roundToPx()) }

    override val captionBar: PlatformInsets
        get() = PlatformInsets(top = 0)

    override val displayCutout: PlatformInsets
        get() = with(density) { PlatformInsets(top = 59.dp.roundToPx()) }

    override val displayCutouts: List<Rect>
        get() = with(density) {
            // Dynamic Island: approximately 126dp wide, 36dp tall, centered at top
            val islandWidth = 126.dp.toPx()
            val islandHeight = 36.dp.toPx()
            val screenWidth = width.toPx()
            val left = (screenWidth - islandWidth) / 2
            val right = left + islandWidth
            listOf(Rect(left, 0f, right, islandHeight))
        }

    override val cutoutPath: Path
        get() = with(density) {
            // Dynamic Island pill shape
            val islandWidth = 126.dp.toPx()
            val islandHeight = 36.dp.toPx()
            val screenWidth = width.toPx()
            val left = (screenWidth - islandWidth) / 2
            val right = left + islandWidth
            val cornerRadius = islandHeight / 2
            Path().apply {
                addRoundRect(
                    androidx.compose.ui.geometry.RoundRect(
                        left = left,
                        top = 11.dp.toPx(), // Dynamic Island starts slightly below top edge
                        right = right,
                        bottom = 11.dp.toPx() + islandHeight,
                        radiusX = cornerRadius,
                        radiusY = cornerRadius
                    )
                )
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
        get() = with(density) { PlatformInsets(top = 59.dp.roundToPx()) }

    override val waterfall: PlatformInsets
        get() = PlatformInsets(0, 0, 0, 0)

    override val width: Dp
        get() = 393.dp

    override val height: Dp
        get() = 852.dp

    override val roundedCornerSize: Dp
        get() = 55.dp
}

