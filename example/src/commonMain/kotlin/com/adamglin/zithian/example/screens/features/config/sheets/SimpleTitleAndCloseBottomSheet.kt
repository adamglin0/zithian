package com.adamglin.zithian.example.screens.features.config.sheets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adamglin.zithian.compose.scaffold.SheetScaffold
import com.adamglin.zithian.compose.sheets.ModalBottomSheet
import com.adamglin.zithian.compose.sheets.header.TitleAndCloseSheetTitle
import com.adamglin.zithian.compose.text.Text
import com.adamglin.zithian.example.screens.LocalExampleAppFontFamily

@Composable
fun SimpleTitleAndCloseBottomSheet(
    isExampleBottomSheetVisible: Boolean,
    onDismissRequest: () -> Unit
) {
    if (isExampleBottomSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = { onDismissRequest() },
        ) {
            SheetScaffold(
                containerRadius = containerRadius,
                header = {
                    TitleAndCloseSheetTitle(
                        title = {
                            Text(
                                "BottomSheet Example",
                                fontFamily = LocalExampleAppFontFamily.current.montserrat
                            )
                        },
                        onClose = { onDismissRequest() }
                    )
                }
            ) {
                Box(modifier = Modifier.fillMaxWidth().height(400.dp))
            }
        }
    }
}
