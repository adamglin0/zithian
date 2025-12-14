package com.adamglin.zithian.compose.sheets.header

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
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
            .fillMaxWidth()
            .background(Color.Red)
            .padding(15.dp),
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
        // 1. Measure close button first (if present) to determine reserved space
        val closeMeasurable = measurables.find { it.layoutId == LAYOUT_ID_CLOSE }
        val closePlaceable = closeMeasurable?.measure(
            Constraints(maxWidth = constraints.maxWidth, maxHeight = constraints.maxHeight)
        )
        val closeWidth = closePlaceable?.width ?: 0
        val closeHeight = closePlaceable?.height ?: 0

        // 2. Measure title with remaining width
        val titleMeasurable = measurables.first { it.layoutId != LAYOUT_ID_CLOSE }
        val titleMaxWidth = (constraints.maxWidth - closeWidth).coerceAtLeast(0)
        val titlePlaceable = titleMeasurable.measure(
            Constraints(maxWidth = titleMaxWidth, maxHeight = constraints.maxHeight)
        )
        val titleHeight = titlePlaceable.height

        // 3. Calculate row height based on the tallest element
        val rowHeight = maxOf(titleHeight, closeHeight)

        // 4. Calculate title's vertical offset (top padding when centered)
        val titleTopOffset = (rowHeight - titleHeight) / 2

        // 5. Apply the same offset as start padding for visual balance
        //    This ensures title's top margin equals its left margin when title is shorter
        val titleStartOffset = titleTopOffset

        // 6. Place elements
        layout(constraints.maxWidth, rowHeight) {
            // Title: offset by calculated start padding, vertically centered
            titlePlaceable.placeRelative(x = titleStartOffset, y = titleTopOffset)

            // Close button: aligned to end, vertically centered
            closePlaceable?.placeRelative(
                x = constraints.maxWidth - closeWidth,
                y = (rowHeight - closeHeight) / 2
            )
        }
    }
}