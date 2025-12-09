package com.adamglin.zithian.example.screens.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.adamglin.zithian.compose.dropdown.DropdownMenu
import com.adamglin.zithian.compose.dropdown.DropdownMenuAnchor
import com.adamglin.zithian.compose.dropdown.SimpleDropdownMenuItem
import com.adamglin.zithian.compose.layout.Gap
import com.adamglin.zithian.compose.layout.HorizontalDivider
import com.adamglin.zithian.compose.text.Text
import com.adamglin.zithian.compose.theme.ZithianTheme

@Composable
fun Other() {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        SectionTitle("DropdownMenuAnchor")
        var mode by remember { mutableStateOf(Mode.Auto) }
        DropdownMenuAnchor {
            current {
                val interactionSource = remember { MutableInteractionSource() }
                Text(
                    modifier = Modifier
                        .clickable(
                            role = Role.Button,
                            interactionSource = interactionSource
                        ) { isMenuVisible = true },
                    text = "Current mode: ${mode.name}"
                )
            }
            menu {
                DropdownMenu {
                    Mode.entries.fastForEach { candidate ->
                        SimpleDropdownMenuItem(
                            isSelected = candidate == mode,
                            onClick = {
                                mode = candidate
                                isMenuVisible = false
                            },
                        ) { Text(candidate.name) }
                    }
                }
            }
        }
        HorizontalDivider()
        SectionTitle("Dialogs")

        HorizontalDivider()

        // HorizontalDivider
        SectionTitle("HorizontalDivider")
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text("Content above divider")
            HorizontalDivider()
            Text("Content below divider")
        }

        Gap(size = 20.dp)
    }
}

@Composable
private fun SectionTitle(title: String) {
    BasicText(
        text = title,
        style = ZithianTheme.typography.titleSmall,
    )
}

private enum class Mode {
    VeryLowPower,
    LowPower,
    Auto,
    HighPower,
    VeryHighPower,
    ExtraHighPower,
}
