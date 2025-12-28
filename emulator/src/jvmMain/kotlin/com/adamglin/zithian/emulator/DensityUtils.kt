@file:OptIn(InternalComposeUiApi::class)

package com.adamglin.zithian.emulator

import androidx.compose.ui.InternalComposeUiApi
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.asSkiaPath
import androidx.compose.ui.platform.PlatformInsets
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import org.jetbrains.skia.Matrix33

/**
 * Creates a new DeviceSpec with insets adjusted for the target density.
 *
 * The original device spec uses a fixed density for calculating inset pixel values.
 * When the component is rendered on a screen with a different density, we need to
 * scale the inset values to match the actual rendering density.
 */
@OptIn(InternalComposeUiApi::class)
internal fun DeviceSpec.adjustedForDensity(targetDensity: Density): DeviceSpec {
    val originalDensity = this.density

    // Calculate the scale ratio between target density and original density
    val scaleRatio = targetDensity.density / originalDensity.density

    return object : DeviceSpec {
        override val density: Density
            get() = targetDensity

        override val name: String
            get() = this@adjustedForDensity.name

        private fun scaleInsets(original: PlatformInsets): PlatformInsets {
            return PlatformInsets(
                left = (original.left * scaleRatio).toInt(),
                top = (original.top * scaleRatio).toInt(),
                right = (original.right * scaleRatio).toInt(),
                bottom = (original.bottom * scaleRatio).toInt()
            )
        }

        override val statusBars: PlatformInsets
            get() = scaleInsets(this@adjustedForDensity.statusBars)

        override val navigationBars: PlatformInsets
            get() = scaleInsets(this@adjustedForDensity.navigationBars)

        override val systemBars: PlatformInsets
            get() = scaleInsets(this@adjustedForDensity.systemBars)

        override val captionBar: PlatformInsets
            get() = scaleInsets(this@adjustedForDensity.captionBar)

        override val displayCutout: PlatformInsets
            get() = scaleInsets(this@adjustedForDensity.displayCutout)

        override val ime: PlatformInsets
            get() = scaleInsets(this@adjustedForDensity.ime)

        override val mandatorySystemGestures: PlatformInsets
            get() = scaleInsets(this@adjustedForDensity.mandatorySystemGestures)

        override val systemGestures: PlatformInsets
            get() = scaleInsets(this@adjustedForDensity.systemGestures)

        override val tappableElement: PlatformInsets
            get() = scaleInsets(this@adjustedForDensity.tappableElement)

        override val waterfall: PlatformInsets
            get() = scaleInsets(this@adjustedForDensity.waterfall)

        // These remain the same as they are Dp values, not pixel values
        override val width: Dp
            get() = this@adjustedForDensity.width

        override val height: Dp
            get() = this@adjustedForDensity.height

        override val roundedCornerSize: Dp
            get() = this@adjustedForDensity.roundedCornerSize

        override val displayCutouts: List<androidx.compose.ui.geometry.Rect>
            get() = this@adjustedForDensity.displayCutouts.map { rect ->
                androidx.compose.ui.geometry.Rect(
                    left = rect.left * scaleRatio,
                    top = rect.top * scaleRatio,
                    right = rect.right * scaleRatio,
                    bottom = rect.bottom * scaleRatio
                )
            }

        override val cutoutPath: Path?
            get() = this@adjustedForDensity.cutoutPath?.let { originalPath ->
                // Scale the path to match the target density
                // Matrix33 is a 3x3 matrix: [a b c] [d e f] [g h i]
                // For scaling: [scaleX 0 0] [0 scaleY 0] [0 0 1]
                val skiaPath = originalPath.asSkiaPath()
                val matrix = Matrix33(
                    scaleRatio, 0f, 0f,      // scaleX, 0, 0
                    0f, scaleRatio, 0f,      // 0, scaleY, 0
                    0f, 0f, 1f               // 0, 0, 1
                )
                skiaPath.transform(matrix)
                skiaPath.asComposePath()
            }
    }
}
