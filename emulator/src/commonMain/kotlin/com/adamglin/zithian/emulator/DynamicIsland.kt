package com.adamglin.zithian.emulator

import androidx.compose.runtime.Composable
import androidx.compose.ui.InternalComposeUiApi
import androidx.compose.ui.geometry.Size

@OptIn(InternalComposeUiApi::class)
@Composable
internal expect fun DynamicIsland(
    size: Size
)