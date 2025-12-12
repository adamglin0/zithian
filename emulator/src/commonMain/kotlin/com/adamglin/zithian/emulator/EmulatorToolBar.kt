package com.adamglin.zithian.emulator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.adamglin.zithian.compose.button.SubtleButton
import com.adamglin.zithian.compose.dropdown.DropdownMenu
import com.adamglin.zithian.compose.dropdown.DropdownMenuAnchor
import com.adamglin.zithian.compose.dropdown.SimpleDropdownMenuItem
import com.adamglin.zithian.compose.text.Text
import com.adamglin.zithian.emulator.config.*
import kotlinx.collections.immutable.persistentListOf

@Composable
fun EmulatorToolBar(
    selectedDevice: Device,
    onDeviceChange: (Device) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(32.dp),
        horizontalArrangement = Arrangement.End,
    ) {
        val density = Density(2f)
        DropdownMenuAnchor {
            current {
                SubtleButton(
                    onClick = { isMenuVisible = true }
                ) { Text(selectedDevice.name) }
            }
            menu {
                DropdownMenu {
                    persistentListOf(
                        Device.iphone14(density = density),
                        Device.iphone16(density = density),
                        Device.iphone17(density = density),
                        Device.iphone17ProMax(density = density),
                        Device.ipadPro11(density = density),
                        Device.ipadPro13(density = density),
                        Device.pixel9Pro(density = density)
                    ).fastForEach {
                        SimpleDropdownMenuItem(
                            isSelected = selectedDevice == it,
                            onClick = { onDeviceChange(it) }
                        ) { Text(it.name) }
                    }
                }
            }
        }
    }
}