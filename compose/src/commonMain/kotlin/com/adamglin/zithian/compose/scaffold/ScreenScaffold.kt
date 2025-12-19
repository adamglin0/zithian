package com.adamglin.zithian.compose.scaffold

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.adamglin.composecontinuousroundedcornershape.ContinuousRoundedCornerShape
import com.adamglin.zithian.compose.theme.ZithianTheme
import com.adamglin.zithian.compose.utils.Device

@Composable
fun ScreenScaffold(
    modifier: Modifier = Modifier,
    header: (@Composable ScaffoldScope.() -> Unit)? = null,
    bottom: (@Composable ScaffoldScope.() -> Unit)? = null,
    backgroundColor: Color = ZithianTheme.colors.background,
    content: @Composable ScaffoldScope.() -> Unit,
) {
    val shadowColor = ZithianTheme.colors.shadow
    CompositionLocalProvider(
        LocalSheetContainerRadius provides Device.windowRoundedCornerSize
    ) {
        BasicScaffold(
            modifier = Modifier
                .dropShadow(ContinuousRoundedCornerShape(20.dp)) {
                    offset = Offset(x = -125f, y = 10f)
                    color = shadowColor
                    spread = -17f
                    radius = 250f
                }
                .fillMaxSize().then(modifier),
            header = header,
            bottom = bottom,
            backgroundColor = backgroundColor,
            content = content,
        )
    }
}