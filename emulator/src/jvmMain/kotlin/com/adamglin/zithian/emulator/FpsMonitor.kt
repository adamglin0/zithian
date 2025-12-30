package com.adamglin.zithian.emulator

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.adamglin.zithian.compose.text.Text

@Composable
internal fun FpsMonitor(modifier: Modifier = Modifier) {
    var fps by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        var frameCount = 0
        var lastTime = withFrameNanos { it }

        while (true) {
            withFrameNanos { currentTime ->
                frameCount++

                val elapsedNanos = currentTime - lastTime
                if (elapsedNanos >= 1_000_000_000L) {
                    fps = frameCount
                    frameCount = 0
                    lastTime = currentTime
                }
            }
        }
    }

    Text(
        text = "$fps FPS",
        modifier = modifier
    )
}