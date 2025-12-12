package com.adamglin.zithian.example.screens.features.components.widgets

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adamglin.zithian.compose.text.Text
import com.adamglin.zithian.compose.theme.ZithianTheme
import com.adamglin.zithian.example.screens.LocalExampleAppFontFamily

@Composable
internal fun SmallTitle(text: String) {
    Text(
        modifier = Modifier.padding(vertical = 2.dp),
        text = text,
        style = ZithianTheme.typography.titleSmall,
        fontFamily = LocalExampleAppFontFamily.current.montserrat
    )
}