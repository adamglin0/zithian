package com.adamglin.zithian.example

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.window.singleWindowApplication
import com.adamglin.zithian.compose.Text
import com.adamglin.zithian.compose.theme.InteractType
import com.adamglin.zithian.compose.theme.ZithianTheme
import com.adamglin.zithian.compose.theme.platformDefault
import com.adamglin.zithian.example.components.Buttons
import com.adamglin.zithian.example.components.Home
import com.adamglin.zithian.example.components.Other

fun main() {
    singleWindowApplication {
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
                Box(modifier = Modifier.weight(1f).padding(10.dp)) {
                    when (selectedTab) {
                        SideBarTab.HOME -> Home(
                            interactType = interactType,
                            onInteractTypeChange = { interactType = it }
                        )

                        SideBarTab.BUTTONS -> Buttons()
                        SideBarTab.OTHER -> Other()
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
                    .padding(4.dp, 2.dp),
                text = tab.name,
            )
        }
    }
}

enum class SideBarTab {
    HOME,
    BUTTONS,
    OTHER,
}