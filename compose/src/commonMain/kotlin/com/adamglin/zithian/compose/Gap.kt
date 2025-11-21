package com.adamglin.zithian.compose

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun RowScope.Gap(
    size: Dp,
    modifier: Modifier = Modifier,
) {
    Spacer(Modifier.width(size) then modifier)
}

@Composable
fun ColumnScope.Gap(
    size: Dp,
    modifier: Modifier = Modifier,
) {
    Spacer(Modifier.height(size) then modifier)
}