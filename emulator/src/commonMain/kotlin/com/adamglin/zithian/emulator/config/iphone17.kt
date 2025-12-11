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
fun Device.Companion.iphone17(density: Density) = Iphone17DeviceParameters(density)

class Iphone17DeviceParameters(override val density: Density) : Device {
    override val statusBars: PlatformInsets
        get() = with(density) { PlatformInsets(top = 60.dp.roundToPx()) }

    override val navigationBars: PlatformInsets
        get() = with(density) { PlatformInsets(bottom = 34.dp.roundToPx()) }

    override val systemBars: PlatformInsets
        get() = with(density) { PlatformInsets(top = 60.dp.roundToPx(), bottom = 34.dp.roundToPx()) }

    override val captionBar: PlatformInsets
        get() = PlatformInsets(top = 0)

    override val displayCutout: PlatformInsets
        get() = PlatformInsets(0, 0, 0, 0)

    override val displayCutouts: List<Rect>
        get() = emptyList()

    override val cutoutPath: Path?
        get() = null

    override val ime: PlatformInsets
        get() = PlatformInsets(bottom = 0)

    override val mandatorySystemGestures: PlatformInsets
        get() = PlatformInsets(bottom = 20)

    override val systemGestures: PlatformInsets
        get() = PlatformInsets(bottom = 25)

    override val tappableElement: PlatformInsets
        get() = PlatformInsets(0, 0, 0, 0)

    override val waterfall: PlatformInsets
        get() = PlatformInsets(0, 0, 0, 0)
    override val width: Dp
        get() = 390.dp
    override val height: Dp
        get() = 844.dp

    override val roundedCornerSize: Dp
        get() = with(density) { 48f.toDp() }
}