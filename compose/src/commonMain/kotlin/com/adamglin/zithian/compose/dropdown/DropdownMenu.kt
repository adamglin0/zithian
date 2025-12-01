package com.adamglin.zithian.compose.dropdown


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.adamglin.composecontinuousroundedcornershape.ContinuousRoundedCornerShape
import com.adamglin.zithian.compose.theme.InteractType
import com.adamglin.zithian.compose.theme.LocalInteractType
import com.adamglin.zithian.compose.theme.LocalZithianSpacing
import com.adamglin.zithian.compose.theme.ZithianSpacing
import com.adamglin.zithian.compose.theme.ZithianTheme
import com.adamglin.zithian.compose.theme.zithianShadowDeep

data class DropdownMenuScope(
    private val columnScope: ColumnScope,
    val dimens: DropdownMenuDimens,
) : ColumnScope by columnScope

data class DropdownMenuDimens(
    val borderRadius: Dp,
    val padding: Dp,
) {
    companion object {
        @Composable
        fun of(
            interactType: InteractType = LocalInteractType.current,
        ): DropdownMenuDimens {
            return DropdownMenuDimens(
                borderRadius = ZithianTheme.shapes.medium,
                padding = ZithianTheme.spacing.small,
            )
        }
    }
}


@Composable
fun DropdownMenu(
    modifier: Modifier = Modifier,
    dimens: DropdownMenuDimens = DropdownMenuDimens.of(),
    content: @Composable DropdownMenuScope.() -> Unit,
) {
    Popup(onDismissRequest = {}) {
        val shape = ContinuousRoundedCornerShape(dimens.borderRadius)
        Column(
            modifier = modifier
                .zithianShadowDeep(shape)
                .background(ZithianTheme.colors.surface, shape)
                .padding(dimens.padding)
        ) {
            DropdownMenuScope(
                columnScope = this,
                dimens = dimens,
            ).apply { content() }
        }
    }
}