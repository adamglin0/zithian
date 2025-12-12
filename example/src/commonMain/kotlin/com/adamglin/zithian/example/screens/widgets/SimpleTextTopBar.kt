package com.adamglin.zithian.example.screens.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adamglin.zithian.compose.generated.resources.ZithianResources
import com.adamglin.zithian.compose.icon.CoilIcon
import com.adamglin.zithian.compose.icon.IconButtonDefaults
import com.adamglin.zithian.compose.icon.OutlinedIconButton
import com.adamglin.zithian.compose.text.Text
import com.adamglin.zithian.compose.theme.ZithianTheme
import com.adamglin.zithian.example.screens.LocalExampleAppFontFamily

@Composable
fun SimpleTextTopBar(
    text: String,
    modifier: Modifier = Modifier,
    onBack: (() -> Unit)? = null
) {
    Row(
        modifier = modifier.fillMaxWidth()
            .background(ZithianTheme.colors.surfacePure)
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .statusBarsPadding(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(modifier = Modifier.height(27.dp)) {
            if (onBack != null) {
                OutlinedIconButton(
                    onClick = { onBack() },
                    dimens = IconButtonDefaults.dimens().copy(contentPadding = PaddingValues(5.dp))
                ) {
                    CoilIcon(
                        uri = ZithianResources.getUri("drawable/ic_chevron_left.svg"),
                        contentDescription = null,
                    )
                }
            }
        }
        Text(
            text = text,
            style = ZithianTheme.typography.titleLarge,
            fontFamily = LocalExampleAppFontFamily.current.montserrat,
        )
    }
}