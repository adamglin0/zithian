package com.adamglin.zithian.example

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.tooling.ComposeStackTraceMode
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.adamglin.zithian.compose.layout.VerticalDivider
import com.adamglin.zithian.compose.text.Text
import com.adamglin.zithian.compose.theme.InteractType
import com.adamglin.zithian.compose.theme.ZithianTheme
import com.adamglin.zithian.compose.theme.platformDefault
import com.adamglin.zithian.example.screens.screens.*

@OptIn(ExperimentalComposeRuntimeApi::class)
fun main() {
    Composer.setDiagnosticStackTraceMode(ComposeStackTraceMode.SourceInformation)
    application {
        Window(onCloseRequest = ::exitApplication) {
            var interactType by remember { mutableStateOf(InteractType.platformDefault) }
            ZithianTheme(
                interactType = interactType
            ) {
                var selectedTab by retain { mutableStateOf(SideBarTab.HOME) }
                Row {
                    SideBar(
                        selectedTab = selectedTab,
                        onSelect = { selectedTab = it }
                    )
                    VerticalDivider()
                    Box(modifier = Modifier.weight(1f)) {
                        when (selectedTab) {
                            SideBarTab.HOME -> Home(
                                interactType = interactType,
                                onInteractTypeChange = { interactType = it }
                            )

                            SideBarTab.BUTTONS -> Buttons()
                            SideBarTab.INPUTS -> Inputs()
                            SideBarTab.SHEETS -> Sheets()
                            SideBarTab.OTHER -> Other()
                            SideBarTab.Texts -> Texts()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SideBar(
    selectedTab: SideBarTab,
    onSelect: (SideBarTab) -> Unit,
) {
    Column(modifier = Modifier.fillMaxHeight().width(IntrinsicSize.Max)) {
        SideBarTab.entries.fastForEach { tab ->
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelect(tab) }
                    .run { if (tab == selectedTab) background(ZithianTheme.colors.primary) else this }
                    .padding(12.dp, 8.dp),
                text = tab.displayName,
            )
        }
    }
}

enum class SideBarTab(val displayName: String) {
    HOME("Home"),
    Texts("Texts"),
    BUTTONS("Buttons"),
    INPUTS("Inputs"),
    SHEETS("Sheets"),
    OTHER("Components"),
}