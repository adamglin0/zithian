package com.adamglin.zithian.compose

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

/**

 * Fillers are a series of common padding elements, whose purposes are:
 * - To create more harmonious spacing across multiple platforms. For example, when NavigationBarPadding has a value on iOS but is 0 on Desktop, the display effect varies significantly.
 * - To provide convenient padding on individual platforms. For instance, when wanting to add content with NavigationBarPadding within a scrollable area.
 */
@Composable
fun BasicFiller(
    modifier: Modifier = Modifier,
    width: Dp = 0.dp,
    height: Dp = 0.dp,
    minWith: Dp = 0.dp,
    minHeight: Dp = 0.dp,
) {
    Spacer(
        modifier = modifier.size(
            width.coerceAtLeast(minWith),
            height.coerceAtLeast(minHeight)
        )
    )
}

@Composable
fun WindowInsetsFiller(
    windowInsets: WindowInsets,
    modifier: Modifier = Modifier,
    minWith: Dp = 0.dp,
    minHeight: Dp = 0.dp,
) {
    windowInsets.asPaddingValues().let { paddingValues ->
        val height = paddingValues.calculateTopPadding() + paddingValues.calculateBottomPadding()
        val width = paddingValues.calculateLeftPadding(LayoutDirection.Ltr) +
                paddingValues.calculateRightPadding(LayoutDirection.Ltr)
        BasicFiller(
            modifier = modifier,
            width = width,
            height = height,
            minWith = minWith,
            minHeight = minHeight,
        )
    }
}