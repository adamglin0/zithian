package com.adamglin.zithian.example.screens.features.config

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adamglin.zithian.compose.button.PrimaryButton
import com.adamglin.zithian.compose.layout.BasicFiller
import com.adamglin.zithian.compose.scaffold.ScreenScaffold
import com.adamglin.zithian.compose.sheets.BottomSheet
import com.adamglin.zithian.compose.sheets.header.TitleAndCloseSheetTitle
import com.adamglin.zithian.compose.text.Text
import com.adamglin.zithian.example.screens.LocalAppState
import com.adamglin.zithian.example.screens.LocalExampleAppFontFamily
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

    BottomSheet(
        isVisible = isExampleBottomSheetVisible,
        onDismissRequest = { isExampleBottomSheetVisible = false },
        header = {
            TitleAndCloseSheetTitle(
                title = { Text("BottomSheet Example", fontFamily = LocalExampleAppFontFamily.current.montserrat) },
                onClose = { isExampleBottomSheetVisible = false }
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxWidth().height(400.dp))
    }
}