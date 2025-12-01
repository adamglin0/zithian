package com.adamglin.zithian.example.screens.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.adamglin.zithian.compose.dropdown.DropdownMenu
import com.adamglin.zithian.compose.dropdown.DropdownMenuAnchor
import com.adamglin.zithian.compose.dropdown.SimpleDropdownMenuItem
import com.adamglin.zithian.compose.layout.Gap
import com.adamglin.zithian.compose.layout.HorizontalDivider
import com.adamglin.zithian.compose.slider.Slider
import com.adamglin.zithian.compose.slider.SliderDefaults
import com.adamglin.zithian.compose.slider.Thumb
import com.adamglin.zithian.compose.slider.rememberSliderState
import com.adamglin.zithian.compose.switch.SmallSwitch
import com.adamglin.zithian.compose.switch.Switch
import com.adamglin.zithian.compose.text.Text
import com.adamglin.zithian.compose.textfield.OutlinedTextField
import com.adamglin.zithian.compose.textfield.PasswordTextField
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
        // Switch
        SectionTitle("Switch")
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            var checked1 by remember { mutableStateOf(false) }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Switch(
                    checked = checked1,
                    onCheckedChange = { checked1 = it },
                )
                Gap(size = 4.dp)
                Text("Normal")
            }

            var checked2 by remember { mutableStateOf(true) }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Switch(
                    checked = checked2,
                    onCheckedChange = { checked2 = it },
                )
                Gap(size = 4.dp)
                Text("Checked")
            }

            var checked3 by remember { mutableStateOf(false) }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                SmallSwitch(
                    checked = checked3,
                    onCheckedChange = { checked3 = it },
                )
                Gap(size = 4.dp)
                Text("Small")
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Switch(
                    checked = true,
                    onCheckedChange = { },
                    enabled = false,
                )
                Gap(size = 4.dp)
                Text("Disabled")
            }
        }

        HorizontalDivider()

        // Slider
        SectionTitle("Slider")
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            val sliderState1 = rememberSliderState(initialValue = 0.5f)
            Column {
                Text("Value: ${String.format("%.2f", sliderState1.value)}")
                Gap(size = 8.dp)
                Slider(
                    state = sliderState1,
                    modifier = Modifier.fillMaxWidth(),
                    track = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(4.dp)
                                .background(ZithianTheme.colors.surface, CircleShape)
                        )
                    },
                    thumb = {
                        Thumb(
                            shape = CircleShape,
                            color = ZithianTheme.colors.primary,
                            dimens = SliderDefaults.dimens()
                        )
                    }
                )
            }

            val sliderState2 = rememberSliderState(initialValue = 0.3f, steps = 4)
            Column {
                Text("With Steps (${sliderState2.value.toInt() * 100}%)")
                Gap(size = 8.dp)
                Slider(
                    state = sliderState2,
                    modifier = Modifier.fillMaxWidth(),
                    track = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(4.dp)
                                .background(ZithianTheme.colors.surface, CircleShape)
                        )
                    },
                    thumb = {
                        Thumb(
                            shape = CircleShape,
                            color = ZithianTheme.colors.primary,
                            dimens = SliderDefaults.dimens()
                        )
                    }
                )
            }
        }

        HorizontalDivider()

        // TextField
        SectionTitle("OutlinedTextField")
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            val textState1 = rememberTextFieldState()
            OutlinedTextField(
                state = textState1,
                modifier = Modifier.fillMaxWidth(),
                placeholdText = "Enter your name...",
            )

            val textState2 = rememberTextFieldState("Pre-filled content")
            OutlinedTextField(
                state = textState2,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        HorizontalDivider()

        // PasswordTextField
        SectionTitle("PasswordTextField")
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            val passwordState = rememberTextFieldState()
            PasswordTextField(
                state = passwordState,
                modifier = Modifier.fillMaxWidth(),
            )
        }

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
