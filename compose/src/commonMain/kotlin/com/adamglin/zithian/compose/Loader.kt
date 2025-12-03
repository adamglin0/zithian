package com.adamglin.zithian.compose

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import com.adamglin.zithian.compose.generated.resources.ZithianResources
import com.adamglin.zithian.compose.icon.CoilIcon

@Composable
fun Loader(
    modifier: Modifier = Modifier,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "loader")

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    CoilIcon(
        uri = ZithianResources.getUri("drawable/ic_loader.svg"),
        contentDescription = "loader",
        modifier = modifier.rotate(rotation),
    )
}