package com.adamglin.zithian.emulator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun rememberEmulatorWindowState(
    deviceSpec: DeviceSpec,
    isAlwaysOnTop: Boolean = false,
): EmulatorWindowState {
    return remember(deviceSpec, isAlwaysOnTop) {
        EmulatorWindowStateImpl(deviceSpec, isAlwaysOnTop)
    }
}

@Stable
interface EmulatorWindowState {
    var deviceSpec: DeviceSpec
    var isAlwaysOnTop: Boolean
}

@Stable
private class EmulatorWindowStateImpl(
    deviceSpec: DeviceSpec,
    isAlwaysOnTop: Boolean,
) : EmulatorWindowState {
    private val _deviceSpec = mutableStateOf(deviceSpec)
    override var deviceSpec: DeviceSpec
        get() = _deviceSpec.value
        set(value) {
            _deviceSpec.value = value
        }

    private val _isAlwaysOnTop = mutableStateOf(isAlwaysOnTop)
    override var isAlwaysOnTop: Boolean
        get() = _isAlwaysOnTop.value
        set(value) {
            _isAlwaysOnTop.value = value
        }
}