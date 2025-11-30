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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.window.Popup
import com.adamglin.composecontinuousroundedcornershape.ContinuousRoundedCornerShape
import com.adamglin.zithian.compose.generated.resources.ZithianResources
import com.adamglin.zithian.compose.icon.CoilIcon
import com.adamglin.zithian.compose.text.Text
import com.adamglin.zithian.compose.theme.InteractType
import com.adamglin.zithian.compose.theme.LocalInteractType
import com.adamglin.zithian.compose.theme.ZithianTheme
import com.adamglin.zithian.compose.utils.shadowBorderWithHover
import kotlinx.collections.immutable.PersistentList

@Immutable
data class PickerDimens(
    val chevronSize: Dp,
    val checkBoxSize: Dp,
    val itemContentPadding: PaddingValues
) {
    companion object {
        internal val Pointer = PickerDimens(
            chevronSize = 17.dp,
            checkBoxSize = 19.dp,
            itemContentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
        )

        internal val Touch = PickerDimens(
            chevronSize = 24.dp,
            checkBoxSize = 24.dp,
            itemContentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        )

        fun of(interactType: InteractType): PickerDimens {
            return when (interactType) {
                InteractType.Pointer -> Pointer
                InteractType.Touch -> Touch
            }
        }
    }
}

object PickerDefaults {
    @Composable
    fun dimens(
        interactType: InteractType = LocalInteractType.current
    ): PickerDimens = PickerDimens.of(interactType)
}

interface PickerScope

@Composable
fun <T> Picker(
    selected: T,
    candidates: PersistentList<T>,
    labelFor: (T) -> String,
    onValueChange: (T) -> Unit,
    modifier: Modifier = Modifier,
    dimens: PickerDimens = PickerDefaults.dimens(),
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
            modifier = Modifier.size(dimens.chevronSize).rotate(180f),
            tint = Color.Black,
            contentDescription = null
        )
    }
    if (isDropdownMenuVisible) {
        Popup(onDismissRequest = { isDropdownMenuVisible = false }) {
            Box(
                modifier = Modifier
                    .shadowBorderWithHover(ContinuousRoundedCornerShape(10.dp))
                    .background(ZithianTheme.colors.surface, ContinuousRoundedCornerShape(10.dp))
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
                                    .size(dimens.checkBoxSize)
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
                                modifier = Modifier.padding(dimens.itemContentPadding),
                                text = labelFor(item)
                            )
                        }
                    }
                }
            }
        }
    }
}
