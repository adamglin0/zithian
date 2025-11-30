package com.adamglin.zithian.example.screens.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.adamglin.zithian.compose.text.Text
import com.adamglin.zithian.compose.theme.ZithianTheme

@Composable
internal fun Texts() {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Text(
            text = "markExtraSmall: This is an extra small mark.",
            style = ZithianTheme.typography.markExtraSmall
        )

        Text(
            text = "markSmall: This is a small mark.",
            style = ZithianTheme.typography.markSmall,
        )

        Text(
            text = "markMedium: This is a medium mark.",
            style = ZithianTheme.typography.markMedium,
        )

        Text(
            text = "markLarge: This is a large mark.",
            style = ZithianTheme.typography.markLarge,
        )

        Text(
            text = "bodyExtraSmall: This is extra small body text.",
            style = ZithianTheme.typography.bodyExtraSmall,
        )

        Text(
            text = "bodySmall: This is small body text.",
            style = ZithianTheme.typography.bodySmall,
        )

        Text(
            text = "bodyMedium: This is medium body text.",
            style = ZithianTheme.typography.bodyMedium,
        )
        Text(
            text = "bodyLarge: This is large body text.",
            style = ZithianTheme.typography.bodyLarge,
        )

        Text(
            text = "titleSmall: This is a small title.",
            style = ZithianTheme.typography.titleSmall,
        )

        Text(
            text = "titleMedium: This is a medium title.",
            style = ZithianTheme.typography.titleMedium,
        )

        Text(
            text = "titleLarge: This is a large title.",
            style = ZithianTheme.typography.titleLarge,
        )

        Text(
            text = "titleExtraLarge: This is an extra large title.",
            style = ZithianTheme.typography.titleExtraLarge,
        )

        Text(
            text = "titleExtraLarge: This is an extra large title.",
            style = ZithianTheme.typography.displayMedium,
        )

        Text(
            text = "titleExtraLarge: This is an extra large title.",
            style = ZithianTheme.typography.displayLarge,
        )
    }
}