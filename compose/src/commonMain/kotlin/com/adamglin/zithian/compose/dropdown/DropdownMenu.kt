package com.adamglin.zithian.compose.dropdown


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Popup
import com.adamglin.composecontinuousroundedcornershape.ContinuousRoundedCornerShape
import com.adamglin.zithian.compose.theme.ZithianTheme
import com.adamglin.zithian.compose.theme.zithianShadowDeep

class DropdownMenuScope(val columnScope: ColumnScope) : ColumnScope by columnScope {

}

@Composable
fun DropdownMenu(
    modifier: Modifier = Modifier,
    content: @Composable DropdownMenuScope.() -> Unit,
) {
    Popup(onDismissRequest = {}){
        val shape = ContinuousRoundedCornerShape(10.dp)
        Column(
            modifier = modifier
                .zithianShadowDeep(shape)
                .background(ZithianTheme.colors.surface, shape)
                .padding(4.dp)
        ) {
            content(DropdownMenuScope(this))
        }
    }
}