package com.adamglin.zithian.compose.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.unit.dp
import com.adamglin.zithian.compose.text.LocalTextStyle
import com.adamglin.zithian.compose.indication.ZithianScaleIndication
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.LocalHazeStyle

@OptIn(ExperimentalTextApi::class)
@Composable
fun ZithianTheme(
    colors: ZithianColors = ZithianColors.light,
    typography: ZithianTypography = ZithianTypography.compat,
    interactType: InteractType= InteractType.platformDefault,
    spacing: ZithianSpacing = ZithianSpacing(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalInteractType provides interactType,
        LocalZithianColors provides colors,
        LocalZithianTypography provides typography,
        LocalZithianSpacing provides spacing,
        LocalIndication provides ZithianScaleIndication,
        LocalTextStyle provides typography.bodyMedium,
        LocalHazeStyle provides HazeStyle(
            backgroundColor = colors.background,
            tint = null,
            blurRadius = 40.dp,
        ),
        LocalContentColor provides colors.text1,
        content = content
    )
}

object ZithianTheme {
    val colors: ZithianColors
        @Composable
        @ReadOnlyComposable
        get() = LocalZithianColors.current
    val typography: ZithianTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalZithianTypography.current
    val spacing: ZithianSpacing
        @Composable
        @ReadOnlyComposable
        get() = LocalZithianSpacing.current
}
