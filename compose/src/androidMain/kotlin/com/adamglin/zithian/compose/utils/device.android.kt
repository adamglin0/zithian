package com.adamglin.zithian.compose.utils

import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

/**
 * CompositionLocal for providing Android Context to zithian compose components
 */
val LocalAppContext = compositionLocalOf<Context> { error("No Android Context provided") }

internal actual fun getDeviceRoundedCornerSize(): Dp {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
        return 30.dp
    }
    // For Android S+, we need a Context to get rounded corners
    // Return default size since we don't have access to Context here
    return 30.dp
}

@Composable
internal actual fun getWindowSize(): DpSize? {
    val density = LocalDensity.current
    val context = LocalAppContext.current
    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
        return getScreenDpSizeForAndroidPreR(density, windowManager)
    }
    return getScreenDpSizeForAndroidR(density, windowManager)
}

@Composable
@RequiresApi(Build.VERSION_CODES.R)
private fun getScreenDpSizeForAndroidR(density: Density, windowManager: WindowManager): DpSize {
    val bounds = windowManager.currentWindowMetrics.bounds
    return with(density) {
        DpSize(
            bounds.width().toDp(),
            bounds.height().toDp()
        )
    }
}

@Suppress("DEPRECATION")
@Composable
private fun getScreenDpSizeForAndroidPreR(density: Density, windowManager: WindowManager): DpSize {
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getRealMetrics(displayMetrics)
    return with(density) {
        DpSize(
            displayMetrics.widthPixels.toDp(),
            displayMetrics.heightPixels.toDp()
        )
    }
}