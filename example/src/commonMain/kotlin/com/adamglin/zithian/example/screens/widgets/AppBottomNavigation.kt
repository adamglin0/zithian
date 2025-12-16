package com.adamglin.zithian.example.screens.widgets

import androidx.compose.runtime.Composable
import com.adamglin.zithian.compose.icon.CoilIcon
import com.adamglin.zithian.compose.navigation.SimpleBottomNavigation
import com.adamglin.zithian.compose.navigation.SimpleBottomNavigationIconItem
import zithian.example.generated.resources.Res

@Composable
fun AppBottomNavigation(
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
) {
    SimpleBottomNavigation(
        selectedIndex = selectedIndex,
        onSelectedIndexChange = { onSelect(it) }
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
