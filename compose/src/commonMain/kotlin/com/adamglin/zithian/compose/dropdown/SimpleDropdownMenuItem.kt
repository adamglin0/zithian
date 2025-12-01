package com.adamglin.zithian.compose.dropdown

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.adamglin.composecontinuousroundedcornershape.ContinuousRoundedCornerShape
import com.adamglin.zithian.compose.generated.resources.ZithianResources
import com.adamglin.zithian.compose.icon.CoilIcon
import com.adamglin.zithian.compose.theme.LocalContentColor
import com.adamglin.zithian.compose.theme.LocalInteractType
import com.adamglin.zithian.compose.theme.ZithianTheme
import com.adamglin.zithian.compose.utils.interactPointer

@Composable
fun DropdownMenuScope.SimpleDropdownMenuItem(
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    enabled: Boolean = true,
    backgroundColor: Color = Color.Transparent,
    foregroundColor: Color = Color.Black,
    hoveredBackgroundColor: Color = ZithianTheme.colors.primary,
    hoveredForegroundColor: Color = ZithianTheme.colors.onPrimary,
    content: @Composable () -> Unit,
) {
    val shape = ContinuousRoundedCornerShape(
        size = (dimens.borderRadius - dimens.padding).coerceAtLeast(0.dp)
    )
    val interactType = LocalInteractType.current
    val isHovered by interactionSource.collectIsHoveredAsState()

    val backgroundColor = when {
        isHovered -> hoveredBackgroundColor
        else -> backgroundColor
    }
    val foregroundColor = when {
        isHovered -> hoveredForegroundColor
        else -> foregroundColor
    }
    CompositionLocalProvider(
        LocalContentColor provides foregroundColor,
    ) {
        Row(
            modifier = modifier
                .clickable(
                    interactionSource = interactionSource,
                    onClick = onClick
                )
                .background(backgroundColor, shape)
                .interactPointer(interactType, enabled)
                // To achieve visual balance, the value of end padding should be much larger.
                .padding(2.dp, 2.dp, 10.dp, 2.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            SelectedIndicator(isSelected)
            Box(modifier = Modifier.weight(1f)) {
                content()
            }
        }
    }
}

@Composable
private fun SelectedIndicator(
    isSelected: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.size(19.dp)) {
        if (isSelected) {
            CoilIcon(
                uri = ZithianResources.getUri("drawable/ic_check.svg"),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
            )
        }
    }
}