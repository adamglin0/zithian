package com.adamglin.zithian.compose


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.adamglin.composecontinuousroundedcornershape.ContinuousRoundedCornerShape
import com.adamglin.zithian.compose.theme.ZithianTheme
import com.adamglin.zithian.compose.theme.zithianShadowDeep

class DropdownMenuScope<T> {
    lateinit var itemsContent: @Composable () -> Unit
    internal var isPopupVisible = mutableStateOf(false)

    @Composable
    fun items(content: @Composable () -> Unit) {
        itemsContent = content
    }

    fun closePopup() {
        isPopupVisible.value = false
    }
}

@Composable
fun <T> DropdownMenu(
    items: @Composable DropdownMenuScope<T>.() -> Unit,
    selected: T?,
    modifier: Modifier = Modifier,
    menuShape: Shape = remember { ContinuousRoundedCornerShape(10.dp) },
    content: @Composable (T?) -> Unit,
) {
    val dropdownMenuScope = remember { DropdownMenuScope<T>() }
    Column(modifier) {
        Box(
            modifier = Modifier
                .clickable { dropdownMenuScope.isPopupVisible.value = !dropdownMenuScope.isPopupVisible.value }
                .background(ZithianTheme.colors.surface, ContinuousRoundedCornerShape(10.dp))
                .padding(20.dp, 6.dp)
        ) {
            content(selected)
        }
        Box {
            if (dropdownMenuScope.isPopupVisible.value) {
                Popup(onDismissRequest = { dropdownMenuScope.isPopupVisible.value = false }) {
                    Column(
                        modifier
                            .width(320.dp)
                            .heightIn(max = 400.dp)
                            .padding(top = 4.dp)
                            .zithianShadowDeep(menuShape)
                            .clip(menuShape)
                            .background(ZithianTheme.colors.text14, menuShape),
                    ) {
                        dropdownMenuScope.items()
                    }
                }
            }
        }
    }
}