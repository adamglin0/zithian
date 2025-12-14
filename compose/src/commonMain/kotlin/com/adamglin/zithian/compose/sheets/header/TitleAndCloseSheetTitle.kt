package com.adamglin.zithian.compose.sheets.header

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.Constraints
import com.adamglin.zithian.compose.annotation.InteractTypeOnly
import com.adamglin.zithian.compose.generated.resources.ZithianResources
import com.adamglin.zithian.compose.icon.CoilIcon
import com.adamglin.zithian.compose.icon.NeutralIconButton
import com.adamglin.zithian.compose.sheets.BasicSheetHeaderScope
import com.adamglin.zithian.compose.text.LocalTextStyle
import com.adamglin.zithian.compose.theme.InteractType
import com.adamglin.zithian.compose.theme.ZithianTheme

private const val LAYOUT_ID_TITLE = "title"
private const val LAYOUT_ID_CLOSE = "close"

/**
 * A sheet header layout that displays a title and an optional close button.
 *
 * This component uses a custom [Layout] to achieve balanced visual spacing:
 * when the title is shorter than the close button, the title's start padding
 * is dynamically adjusted to match its top padding, creating a visually
 * consistent margin from the header boundaries.
 *
 * @param title The title content to display.
 * @param onClose Optional callback invoked when the close button is clicked.
 *                If null, the close button is not shown.
 */
@Composable
@InteractTypeOnly(InteractType.Touch)
fun BasicSheetHeaderScope.TitleAndCloseSheetTitle(
    title: @Composable () -> Unit,
    onClose: (() -> Unit)? = null,
) {
    val headerHeight = containerRadius * 2
    Layout(
        modifier = Modifier
            .height(headerHeight)
            .fillMaxWidth(),
        content = {
            CompositionLocalProvider(
                LocalTextStyle provides ZithianTheme.typography.titleMedium
            ) {
                title()
            }
            onClose?.let { onCloseNotNull ->
                NeutralIconButton(
                    onClick = onCloseNotNull,
                    modifier = Modifier.layoutId(LAYOUT_ID_CLOSE),
                ) {
                    CoilIcon(
                        uri = ZithianResources.getUri("drawable/ic_x.svg"),
                        contentDescription = null,
                    )
                }
            }
        },
    ) { measurables, constraints ->
        val headerHeightPx = constraints.maxHeight

        // 1. Measure close button first (if present)
        val closeMeasurable = measurables.find { it.layoutId == LAYOUT_ID_CLOSE }
        val closePlaceable = closeMeasurable?.measure(
            Constraints(maxWidth = constraints.maxWidth, maxHeight = constraints.maxHeight)
        )
        val closeWidth = closePlaceable?.width ?: 0
        val closeHeight = closePlaceable?.height ?: 0

        // 2. Calculate close button padding independently: (headerHeight - iconButtonHeight) / 2
        val closePadding = (headerHeightPx - closeHeight) / 2

        // 3. Measure title with remaining width (accounting for close button and its padding)
        val titleMeasurable = measurables.first { it.layoutId != LAYOUT_ID_CLOSE }
        val closeReservedWidth = if (closePlaceable != null) closeWidth + closePadding else 0
        val titleMaxWidth = (constraints.maxWidth - closeReservedWidth).coerceAtLeast(0)
        val titlePlaceable = titleMeasurable.measure(
            Constraints(maxWidth = titleMaxWidth, maxHeight = constraints.maxHeight)
        )
        val titleHeight = titlePlaceable.height

        // 4. Calculate title padding independently: (headerHeight - titleHeight) / 2
        val titlePadding = (headerHeightPx - titleHeight) / 2

        // 5. Place elements
        layout(constraints.maxWidth, headerHeightPx) {
            // Title: use titlePadding for both start and top offset
            titlePlaceable.placeRelative(x = titlePadding, y = titlePadding)

            // Close button: aligned to end with closePadding, vertically centered with closePadding
            closePlaceable?.placeRelative(
                x = constraints.maxWidth - closeWidth - closePadding,
                y = closePadding
            )
        }
    }
}