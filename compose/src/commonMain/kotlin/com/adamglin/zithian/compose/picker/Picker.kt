package com.adamglin.zithian.compose.picker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.window.Popup
import com.adamglin.zithian.compose.generated.resources.ZithianResources
import com.adamglin.zithian.compose.icon.CoilIcon
import com.adamglin.zithian.compose.text.Text
import com.adamglin.zithian.compose.theme.ZithianTheme
import kotlinx.collections.immutable.PersistentList

interface PickerScope

@Composable
fun <T> Picker(
    selected: T,
    candidates: PersistentList<T>,
    labelFor: (T) -> String,
    onValueChange: (T) -> Unit,
    modifier: Modifier = Modifier,
) {
    val labelForSelected = remember(selected) { labelFor(selected) }
    var isDropdownMenuVisible by remember { mutableStateOf(false) }
    Row(
        modifier = modifier
            .clickable { isDropdownMenuVisible = true },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(labelForSelected)
        CoilIcon(
            uri = ZithianResources.getUri("drawable/ic_chevron_left.svg"),
            modifier = Modifier.size(17.dp).rotate(180f),
            tint = Color.Black,
            contentDescription = null
        )
    }
    if (isDropdownMenuVisible) {
        Popup(onDismissRequest = { isDropdownMenuVisible = false }) {
            Box(
                modifier = Modifier
                    .shadow(10.dp)
                    .background(ZithianTheme.colors.surface)
            ) {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .heightIn(max = 400.dp)
                ) {
                    candidates.fastForEach { item ->
                        val interactionSource = remember { MutableInteractionSource() }
                        Row(
                            modifier = Modifier
                                .hoverable(interactionSource = interactionSource)
                                .clickable(interactionSource = interactionSource) {
                                    onValueChange(item)
                                    isDropdownMenuVisible = false
                                },
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(19.dp)
                            ) {
                                if (item == selected) {
                                    CoilIcon(
                                        uri = ZithianResources.getUri("drawable/ic_check.svg"),
                                        tint = ZithianTheme.colors.primary,
                                        contentDescription = null
                                    )
                                }
                            }
                            Text(
                                modifier = Modifier.padding(10.dp, 4.dp),
                                text = labelFor(item)
                            )
                        }
                    }
                }
            }
        }
    }
}