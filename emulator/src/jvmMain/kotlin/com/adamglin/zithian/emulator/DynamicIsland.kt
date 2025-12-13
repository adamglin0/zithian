package com.adamglin.zithian.emulator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.InternalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import com.adamglin.composecontinuousroundedcornershape.ContinuousRoundedCornerShape

@OptIn(InternalComposeUiApi::class)
@Composable
internal fun DynamicIsland(size: DpSize) {
    Box(
        modifier = Modifier.fillMaxWidth()
            .height(WindowInsets.systemBars.asPaddingValues().calculateTopPadding()),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .background(Color.Black, ContinuousRoundedCornerShape(100f))
                .size(size)
        )
    }
}