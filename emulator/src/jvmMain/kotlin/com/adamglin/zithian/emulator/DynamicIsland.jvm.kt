package com.adamglin.zithian.emulator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.adamglin.composecontinuousroundedcornershape.ContinuousRoundedCornerShape

@OptIn(markerClass = [androidx.compose.ui.InternalComposeUiApi::class])
@androidx.compose.runtime.Composable
internal actual fun DynamicIsland(size: androidx.compose.ui.geometry.Size) {
    Box(
        modifier = Modifier.fillMaxWidth()
            .height(WindowInsets.systemBars.asPaddingValues().calculateTopPadding()),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .background(Color.Black, ContinuousRoundedCornerShape(100f))
                .size(124.dp, 36.dp)
        )
    }
}
