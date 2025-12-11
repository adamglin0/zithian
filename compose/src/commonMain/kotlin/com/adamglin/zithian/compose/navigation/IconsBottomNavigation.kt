package com.adamglin.zithian.compose.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

interface IconsBottomNavigationScope {
    fun item(content: @Composable () -> Unit)
}

@Composable
fun IconsBottomNavigation(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 20.dp),
    content: @Composable () -> Unit,
) {
    Row(
        modifier = modifier
            .padding(contentPadding)
    ) {
        Box(modifier = Modifier.weight(1f)) {

        }
    }
}