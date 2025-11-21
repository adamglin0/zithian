package com.adamglin.zithian.compose.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.uikit.LocalUIViewController
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ULongVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.toKString
import kotlinx.cinterop.useContents
import kotlinx.cinterop.value
import platform.UIKit.UIScreen
import platform.darwin.sysctlbyname


private val cornerRadiusTable: Map<String, Dp> = mapOf(
    // iPhone X, Xs, Xs Max, 11 Pro, 11 Pro Max — 39.0 pts
    "iPhone10,3" to 39.dp, // iPhone X (Global)
    "iPhone10,6" to 39.dp, // iPhone X (GSM)
    "iPhone11,2" to 39.dp, // iPhone Xs
    "iPhone11,4" to 39.dp, // iPhone Xs Max (China)
    "iPhone11,6" to 39.dp, // iPhone Xs Max (Global)
    "iPhone12,3" to 39.dp, // iPhone 11 Pro
    "iPhone12,5" to 39.dp, // iPhone 11 Pro Max
    // iPhone Xr, 11 — 41.5 pts
    "iPhone11,8" to 41.5.dp, // iPhone Xr
    "iPhone12,1" to 41.5.dp, // iPhone 11
    // iPhone 12 mini, 13 mini — 44.0 pts
    "iPhone13,1" to 44.dp, // iPhone 12 mini
    "iPhone14,4" to 44.dp, // iPhone 13 mini
    // iPhone 12, 12 Pro, 13 Pro, 14, 16e — 47.33 pts
    "iPhone13,2" to 47.33.dp, // iPhone 12
    "iPhone13,3" to 47.33.dp, // iPhone 12 Pro
    "iPhone14,2" to 47.33.dp, // iPhone 13 Pro
    "iPhone14,7" to 47.33.dp, // iPhone 14
    "iPhone18,8" to 47.33.dp, // iPhone 16e（假设型号）
    // iPhone 12 Pro Max, 13 Pro Max, 14 Plus — 53.33 pts
    "iPhone13,4" to 53.33.dp, // iPhone 12 Pro Max
    "iPhone14,3" to 53.33.dp, // iPhone 13 Pro Max
    "iPhone14,8" to 53.33.dp, // iPhone 14 Plus
    // iPhone 14 Pro, 14 Pro Max, 15, 15 Plus, 15 Pro, 15 Pro Max, 16, 16 Plus — 55.0 pts
    "iPhone15,2" to 55.dp, // iPhone 14 Pro
    "iPhone15,3" to 55.dp, // iPhone 14 Pro Max
    "iPhone15,4" to 55.dp, // iPhone 15
    "iPhone15,5" to 55.dp, // iPhone 15 Plus
    "iPhone16,1" to 55.dp, // iPhone 15 Pro
    "iPhone16,2" to 55.dp, // iPhone 15 Pro Max
    "iPhone17,1" to 55.dp, // iPhone 16
    "iPhone17,2" to 55.dp, // iPhone 16 Plus
    // iPhone 16 Pro, 16 Pro Max, 17, 17 Pro, 17 Pro Max, Air — 62.0 pts
    "iPhone17,3" to 62.dp, // iPhone 16 Pro
    "iPhone17,4" to 62.dp, // iPhone 16 Pro Max
    "iPhone18,1" to 62.dp, // iPhone 17
    "iPhone18,2" to 62.dp, // iPhone 17 Pro
    "iPhone18,3" to 62.dp, // iPhone 17 Pro Max
    "iPhone18,4" to 62.dp, // iPhone Air
    // iPad Air / iPad Pro 11-inch / 12.9-inch — 18.0 pts
    "iPad13,16" to 18.dp, // iPad Air (M1)
    "iPad13,17" to 18.dp, // iPad Air (M1)
    "iPad14,3" to 18.dp, // iPad Air (M2)
    "iPad14,4" to 18.dp, // iPad Air (M2)
    "iPad8,1" to 18.dp, // iPad Pro 11" (1st Gen)
    "iPad8,5" to 18.dp, // iPad Pro 12.9" (3rd Gen)
    "iPad8,9" to 18.dp, // iPad Pro 11" (2nd Gen)
    "iPad8,13" to 18.dp, // iPad Pro 12.9" (4th Gen)
    "iPad13,4" to 18.dp, // iPad Pro 11" (M1)
    "iPad13,8" to 18.dp, // iPad Pro 12.9" (M1)
    "iPad14,5" to 18.dp, // iPad Pro 11" (M2)
    "iPad14,1" to 18.dp  // iPad Pro 12.9" (M2)
)

internal actual fun getDeviceRoundedCornerSize(): Dp {
    val identifier = getHardwareIdentifier()
    return identifier?.let { cornerRadiusTable[it] } ?: cornerRadiusTable["iPhone18,1"]!!
}

fun getHardwareIdentifier(): String? = memScoped {
    val name = "hw.machine"
    // 先获取长度
    val lengthVar = alloc<ULongVar>()
    if (sysctlbyname(name, null, lengthVar.ptr, null, 0u) != 0) {
        return null
    }
    // 再获取值
    val buf = allocArray<ByteVar>(lengthVar.value.toInt())
    if (sysctlbyname(name, buf, lengthVar.ptr, null, 0u) != 0) {
        return null
    }
    return buf.toKString()
}

@Composable
internal actual fun getWindowSize(): DpSize? {
    val density = LocalDensity.current
    val controller = LocalUIViewController.current
    val bounds = controller.view.window?.bounds ?: UIScreen.mainScreen.bounds
    val scale = UIScreen.mainScreen.scale

    return bounds.useContents {
        with(density) {
            DpSize(
                width = (size.width.toFloat() * scale.toFloat()).toDp(),
                height = (size.height.toFloat() * scale.toFloat()).toDp()
            )
        }
    }
}