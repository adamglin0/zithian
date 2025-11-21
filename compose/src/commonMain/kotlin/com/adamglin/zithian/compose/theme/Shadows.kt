package com.adamglin.zithian.compose.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.zithianShadow1(shape: Shape) = this
    .dropShadow(
        shape = shape,
        shadow = Shadow(
            radius = 0.dp,
            color = ZithianTheme.colors.shadow,
            offset = DpOffset(x = 0.dp, y = (-1).dp),
            spread = 0.dp
        )
    )
    .dropShadow(
        shape = shape,
        shadow = Shadow(
            radius = 1.dp,
            color = ZithianTheme.colors.shadow,
            spread = 0.dp
        )
    )
    .dropShadow(
        shape = shape,
        shadow = Shadow(
            radius = 1.dp,
            offset = DpOffset(x = 0.dp, y = 1.dp),
            color = ZithianTheme.colors.shadow,
            spread = 0.dp,
        )
    )
    .dropShadow(
        shape = shape,
        shadow = Shadow(
            radius = 2.dp,
            offset = DpOffset(x = 0.dp, y = 2.dp),
            color = ZithianTheme.colors.shadow,
            spread = 0.dp,
        )
    )

@Composable
fun Modifier.zithianShadow2(shape: Shape) = this
    .dropShadow(
        shape = shape,
        shadow = Shadow(
            radius = 0.dp,
            color = ZithianTheme.colors.shadow,
            offset = DpOffset(x = 0.dp, y = (-1).dp),
            spread = 0.dp
        )
    )
    .dropShadow(
        shape = shape,
        shadow = Shadow(
            radius = 1.dp,
            color = ZithianTheme.colors.shadow,
            spread = 0.dp
        )
    )
    .dropShadow(
        shape = shape,
        shadow = Shadow(
            radius = 1.dp,
            offset = DpOffset(x = 0.dp, y = 1.dp),
            color = ZithianTheme.colors.shadow,
            spread = 0.dp,
        )
    )
    .dropShadow(
        shape = shape,
        shadow = Shadow(
            radius = 2.dp,
            offset = DpOffset(x = 0.dp, y = 2.dp),
            color = ZithianTheme.colors.shadow,
            spread = 0.dp,
        )
    )
    .dropShadow(
        shape = shape,
        shadow = Shadow(
            radius = 4.dp,
            offset = DpOffset(x = 0.dp, y = 4.dp),
            color = ZithianTheme.colors.shadow,
            spread = 0.dp,
        )
    )
    .dropShadow(
        shape = shape,
        shadow = Shadow(
            radius = 8.dp,
            offset = DpOffset(x = 0.dp, y = 8.dp),
            color = ZithianTheme.colors.shadow,
            spread = 0.dp,
        )
    )
    .dropShadow(
        shape = shape,
        shadow = Shadow(
            radius = 16.dp,
            offset = DpOffset(x = 0.dp, y = 16.dp),
            color = ZithianTheme.colors.shadow,
            spread = 0.dp,
        )
    )

@Composable
fun Modifier.zithianShadowDeep(shape: Shape) = this
    .dropShadow(
        shape = shape,
        shadow = Shadow(
            radius = 0.dp,
            color = ZithianTheme.colors.shadow,
            offset = DpOffset(x = 0.dp, y = (-1).dp),
            spread = 0.dp
        )
    )
    .dropShadow(
        shape = shape,
        shadow = Shadow(
            radius = 1.dp,
            color = ZithianTheme.colors.shadow,
            spread = 0.dp
        )
    )
    .dropShadow(
        shape = shape,
        shadow = Shadow(
            radius = 1.dp,
            offset = DpOffset(x = 0.dp, y = 1.dp),
            color = ZithianTheme.colors.shadow,
            spread = 0.dp,
        )
    )
    .dropShadow(
        shape = shape,
        shadow = Shadow(
            radius = 2.dp,
            offset = DpOffset(x = 0.dp, y = 2.dp),
            color = ZithianTheme.colors.shadow,
            spread = 0.dp,
        )
    )
    .dropShadow(
        shape = shape,
        shadow = Shadow(
            radius = 4.dp,
            offset = DpOffset(x = 0.dp, y = 4.dp),
            color = ZithianTheme.colors.shadow,
            spread = 0.dp,
        )
    )
    .dropShadow(
        shape = shape,
        shadow = Shadow(
            radius = 8.dp,
            offset = DpOffset(x = 0.dp, y = 8.dp),
            color = ZithianTheme.colors.shadow,
            spread = 0.dp,
        )
    )
    .dropShadow(
        shape = shape,
        shadow = Shadow(
            radius = 16.dp,
            offset = DpOffset(x = 0.dp, y = 16.dp),
            color = ZithianTheme.colors.shadow,
            spread = 0.dp,
        )
    )