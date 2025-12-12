package com.adamglin.zithian.emulator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adamglin.zithian.compose.button.SubtleButton
import com.adamglin.zithian.compose.dropdown.DropdownMenu
import com.adamglin.zithian.compose.dropdown.DropdownMenuAnchor
import com.adamglin.zithian.compose.dropdown.SimpleDropdownMenuItem
import com.adamglin.zithian.compose.text.Text

@Composable
fun EmulatorToolBar(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(32.dp),
        horizontalArrangement = Arrangement.End,
    ) {
        DropdownMenuAnchor {
            current {
                SubtleButton(
                    onClick = { isMenuVisible = true }
                ) { Text("Device") }
            }
            menu {
                DropdownMenu {
                    SimpleDropdownMenuItem(
                        isSelected = true,
                        onClick = {}
                    ) { Text("Iphone 17") }
                }
            }
        }
    }
}