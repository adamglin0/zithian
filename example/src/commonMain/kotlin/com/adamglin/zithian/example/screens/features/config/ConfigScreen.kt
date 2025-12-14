package com.adamglin.zithian.example.screens.features.config

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adamglin.zithian.compose.button.NeutralButton
import com.adamglin.zithian.compose.button.PrimaryButton
import com.adamglin.zithian.compose.dialog.BasicDialog
import com.adamglin.zithian.compose.layout.BasicFiller
import com.adamglin.zithian.compose.scaffold.ScreenScaffold
import com.adamglin.zithian.compose.text.Text
import com.adamglin.zithian.compose.textfield.OutlinedTextField
import com.adamglin.zithian.compose.theme.ZithianTheme
import com.adamglin.zithian.example.screens.LocalAppState
import com.adamglin.zithian.example.screens.LocalExampleAppFontFamily
import com.adamglin.zithian.example.screens.features.config.sheets.SimpleTitleAndCloseBottomSheet
import com.adamglin.zithian.example.screens.features.liquid_test.LiquidTestNavKey
import com.adamglin.zithian.example.screens.widgets.SimpleTextTopBar
import com.adamglin.zithian.example.screens.widgets.TopLevelSharableBottomNavigation

@Composable
fun ConfigScreen() {
    val appState = LocalAppState.current
    var isExampleBottomSheetVisible by remember { mutableStateOf(false) }
    ScreenScaffold(
        header = {
            SimpleTextTopBar("Config")
        },
        bottom = { TopLevelSharableBottomNavigation() }
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            BasicFiller(height = headerHeight + 10.dp)
            PrimaryButton(
                onClick = {
                    appState.navigate {
                        add(LiquidTestNavKey)
                    }
                },
            ) { Text("Navigate to Next Screen") }
            PrimaryButton(
                onClick = { isExampleBottomSheetVisible = true },
            ) { Text("Bottom Sheet") }
        }
    }
    SimpleTitleAndCloseBottomSheet(
        isExampleBottomSheetVisible,
        onDismissRequest = { isExampleBottomSheetVisible = false }
    )
    BasicDialog(
        isVisible = true,
    ) {
        Column(modifier = Modifier.background(ZithianTheme.colors.surface).padding(20.dp)) {
            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = "Enter Password",
                    fontFamily = LocalExampleAppFontFamily.current.montserrat,
                    style = ZithianTheme.typography.titleMedium
                )
                BasicFiller(height = 8.dp)
                Text(
                    text = "To proceed, please enter your password for adamglin.com",
                    style = ZithianTheme.typography.bodyMedium,
                    color = ZithianTheme.colors.text7
                )
                BasicFiller(height = 10.dp)
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    state = rememberTextFieldState()
                )
            }
            BasicFiller(height = 0.dp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                NeutralButton(
                    modifier = Modifier.weight(1f),
                    onClick = {}
                ) { Text("Cancel") }
                PrimaryButton(
                    modifier = Modifier.weight(1f),
                    onClick = {}
                ) { Text("OK") }
            }
        }
    }
}