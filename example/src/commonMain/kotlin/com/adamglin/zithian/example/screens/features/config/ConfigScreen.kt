package com.adamglin.zithian.example.screens.features.config

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adamglin.zithian.compose.button.PrimaryButton
import com.adamglin.zithian.compose.layout.BasicFiller
import com.adamglin.zithian.compose.scaffold.ScreenScaffold
import com.adamglin.zithian.compose.text.Text
import com.adamglin.zithian.example.screens.LocalAppState
import com.adamglin.zithian.example.screens.features.config.sheets.NonModalBottomSheetExample
import com.adamglin.zithian.example.screens.features.config.sheets.SheetScaffoldExampleBottomSheet
import com.adamglin.zithian.example.screens.features.config.sheets.SimpleSheetScaffoldExampleBottomSheet
import com.adamglin.zithian.example.screens.features.config.sheets.SimpleTitleAndCloseBottomSheet
import com.adamglin.zithian.example.screens.features.liquid_test.LiquidTestNavKey
import com.adamglin.zithian.example.screens.widgets.SimpleTextTopBar
import com.adamglin.zithian.example.screens.widgets.TopLevelSharableBottomNavigation

@Composable
fun ConfigScreen() {
    val appState = LocalAppState.current
    var isExampleBottomSheetVisible by remember { mutableStateOf(false) }
    var isSheetScaffoldExampleVisible by remember { mutableStateOf(false) }
    var isSimpleSheetScaffoldExampleVisible by remember { mutableStateOf(false) }
    var isNonModalBottomSheetVisible by remember { mutableStateOf(false) }
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
            ) { Text("Simple Bottom Sheet") }
            PrimaryButton(
                onClick = { isSheetScaffoldExampleVisible = true },
            ) { Text("SheetScaffold (3-Section)") }
            PrimaryButton(
                onClick = { isSimpleSheetScaffoldExampleVisible = true },
            ) { Text("SimpleSheetScaffold (Floating Close)") }
            PrimaryButton(
                onClick = { isNonModalBottomSheetVisible = true },
            ) { Text("Non-Modal BottomSheet") }
        }
    }
    SimpleTitleAndCloseBottomSheet(
        isExampleBottomSheetVisible,
        onDismissRequest = { isExampleBottomSheetVisible = false }
    )
    SheetScaffoldExampleBottomSheet(
        isSheetScaffoldExampleVisible,
        onDismissRequest = { isSheetScaffoldExampleVisible = false }
    )
    SimpleSheetScaffoldExampleBottomSheet(
        isSimpleSheetScaffoldExampleVisible,
        onDismissRequest = { isSimpleSheetScaffoldExampleVisible = false }
    )
    NonModalBottomSheetExample(
        isNonModalBottomSheetVisible,
        onDismissRequest = { isNonModalBottomSheetVisible = false }
    )
}
