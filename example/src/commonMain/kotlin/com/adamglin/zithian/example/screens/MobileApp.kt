package com.adamglin.zithian.example.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.adamglin.zithian.compose.icon.CoilIcon
import com.adamglin.zithian.compose.navigation.SimpleBottomNavigation
import com.adamglin.zithian.compose.navigation.SimpleBottomNavigationIconItem
import com.adamglin.zithian.compose.scaffold.ScreenScaffold
import zithian.example.generated.resources.Res

@Composable
fun MobileApp() {
    var selectedIndex by remember { mutableStateOf(0) }

    ScreenScaffold {
        Column {
            Box(modifier = Modifier.weight(1f))
            SimpleBottomNavigation(
                selectedIndex = selectedIndex,
                onSelectedIndexChange = { selectedIndex = it }
            ) {
                item {
                    SimpleBottomNavigationIconItem(
                        selected = selected,
                        onClick = ::select,
                        icon = {
                            CoilIcon(
                                uri = Res.getUri("drawable/ic_flower.svg"),
                                contentDescription = null
                            )
                        },
                        selectedIcon = {
                            CoilIcon(
                                uri = Res.getUri("drawable/ic_flower_filled.svg"),
                                contentDescription = null
                            )
                        }
                    )
                }
                item {
                    SimpleBottomNavigationIconItem(
                        selected = selected,
                        onClick = ::select,
                        icon = {
                            CoilIcon(
                                uri = Res.getUri("drawable/ic_components.svg"),
                                contentDescription = null
                            )
                        },
                        selectedIcon = {
                            CoilIcon(
                                uri = Res.getUri("drawable/ic_components_filled.svg"),
                                contentDescription = null
                            )
                        }
                    )
                }
                item {
                    SimpleBottomNavigationIconItem(
                        selected = selected,
                        onClick = ::select,
                        icon = {
                            CoilIcon(
                                uri = Res.getUri("drawable/ic_butterfly.svg"),
                                contentDescription = null
                            )
                        },
                        selectedIcon = {
                            CoilIcon(
                                uri = Res.getUri("drawable/ic_butterfly_filled.svg"),
                                contentDescription = null
                            )
                        }
                    )
                }
            }
        }
    }
}