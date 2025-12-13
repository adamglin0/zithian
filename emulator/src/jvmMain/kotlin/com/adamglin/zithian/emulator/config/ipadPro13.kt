@file:OptIn(InternalComposeUiApi::class)

package com.adamglin.zithian.emulator.config

import androidx.compose.ui.InternalComposeUiApi
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.PlatformInsets
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.adamglin.zithian.emulator.DeviceSpec

@OptIn(InternalComposeUiApi::class)
fun DeviceSpec.Companion.ipadPro13(density: Density) = IpadPro13DeviceParameters(density)

class IpadPro13DeviceParameters(override val density: Density) : DeviceSpec {
    override val name: String
        get() = "iPad Pro 13\""

    override val statusBars: PlatformInsets
        get() = with(density) { PlatformInsets(top = 24.dp.roundToPx()) }

    override val navigationBars: PlatformInsets
        get() = with(density) { PlatformInsets(bottom = 20.dp.roundToPx()) }

    override val systemBars: PlatformInsets
        get() = with(density) { PlatformInsets(top = 24.dp.roundToPx(), bottom = 20.dp.roundToPx()) }

    override val captionBar: PlatformInsets
        get() = PlatformInsets(top = 0)

    override val displayCutout: PlatformInsets
        get() = PlatformInsets(top = 0)

    override val displayCutouts: List<Rect>
        get() = emptyList() // iPad Pro has no notch or cutout

    override val cutoutPath: Path
        get() = Path() // No cutout

    override val ime: PlatformInsets
        get() = PlatformInsets(bottom = 0)

    override val mandatorySystemGestures: PlatformInsets
        get() = with(density) { PlatformInsets(bottom = 20.dp.roundToPx()) }

    override val systemGestures: PlatformInsets
        get() = with(density) {
            PlatformInsets(
                left = 20.dp.roundToPx(),
                right = 20.dp.roundToPx(),
                bottom = 20.dp.roundToPx()
            )
        }

    override val tappableElement: PlatformInsets
        get() = with(density) { PlatformInsets(top = 24.dp.roundToPx()) }

    override val waterfall: PlatformInsets
        get() = PlatformInsets(0, 0, 0, 0)

    override val width: Dp
        get() = 1024.dp

    override val height: Dp
        get() = 1366.dp

    override val roundedCornerSize: Dp
        get() = 18.dp
}

