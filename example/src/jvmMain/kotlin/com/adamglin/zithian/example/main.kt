package com.adamglin.zithian.example

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composer
import androidx.compose.runtime.ExperimentalComposeRuntimeApi
import androidx.compose.runtime.tooling.ComposeStackTraceMode
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.window.application
import com.adamglin.zithian.compose.text.Text
import com.adamglin.zithian.compose.theme.ZithianTheme
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalComposeRuntimeApi::class)
fun main() {
    Composer.setDiagnosticStackTraceMode(ComposeStackTraceMode.SourceInformation)
    runBlocking {
//        launch {
//            pointerApplication()
//        }
        touchApplication()
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