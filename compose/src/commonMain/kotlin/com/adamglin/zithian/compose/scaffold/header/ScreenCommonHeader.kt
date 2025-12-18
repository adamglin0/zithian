package com.adamglin.zithian.compose.scaffold.header

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.adamglin.zithian.compose.annotation.InteractTypeOnly
import com.adamglin.zithian.compose.generated.resources.ZithianResources
import com.adamglin.zithian.compose.icon.CoilIcon
import com.adamglin.zithian.compose.scaffold.ScaffoldScope
import com.adamglin.zithian.compose.text.LocalTextStyle
import com.adamglin.zithian.compose.theme.InteractType
import com.adamglin.zithian.compose.theme.ZithianTheme
import dev.chrisbanes.haze.HazeProgressive
import dev.chrisbanes.haze.hazeEffect
import io.github.fletchmckee.liquid.liquid
import kotlin.math.max

/**
 * CompositionLocal for providing [SharedTransitionScope] to [ScreenCommonHeader].
 *
 * When provided, [ScreenCommonHeader] will automatically use this scope for shared element
 * transitions without needing to pass it explicitly as a parameter.
 *
 * Example:
 * ```kotlin
 * SharedTransitionLayout {
 *     CompositionLocalProvider(
 *         LocalScreenCommonHeaderSharedTransitionScope provides this
 *     ) {
 *         // All ScreenCommonHeader instances will automatically participate in shared transitions
 *         ScreenCommonHeader(title = { Text("Title") })
 *     }
 * }
 * ```
 */
@Suppress("CompositionLocalAllowlist")
val LocalScreenCommonHeaderSharedTransitionScope = staticCompositionLocalOf<SharedTransitionScope?> { null }

/**
 * Dimensions configuration for [ScreenCommonHeader].
 *
 * @property height The total height of the header (excluding status bar).
 * @property horizontalPadding The horizontal padding of the header content.
 * @property minSpacing The minimum spacing between leading, title, and actions.
 * @property hazeBlurRadius The blur radius for the haze effect.
 */
@Immutable
data class ScreenCommonHeaderDimens(
    val height: Dp,
    val horizontalPadding: Dp,
    val minSpacing: Dp,
    val hazeBlurRadius: Dp,
) {
    companion object {
        internal val Touch = ScreenCommonHeaderDimens(
            height = 56.dp,
            horizontalPadding = 16.dp,
            minSpacing = 12.dp,
            hazeBlurRadius = 6.dp,
        )

        fun of(interactType: InteractType): ScreenCommonHeaderDimens {
            return when (interactType) {
                InteractType.Pointer -> Touch // For now, use same dimensions
                InteractType.Touch -> Touch
            }
        }
    }
}

internal object ScreenCommonHeaderDefaults {
    @Composable
    fun dimens(
        interactType: InteractType = com.adamglin.zithian.compose.theme.LocalInteractType.current
    ): ScreenCommonHeaderDimens = ScreenCommonHeaderDimens.of(interactType)
}

/**
 * Keys for shared element transitions in [ScreenCommonHeader].
 *
 * Use these keys when you need to customize shared element behavior or
 * connect with other composables outside of ScreenCommonHeader.
 */
object ScreenCommonHeaderSharedElementKey {
    const val LEADING = "screen_common_header_leading"
    const val TITLE = "screen_common_header_title"
    const val ACTIONS = "screen_common_header_actions"
}

/**
 * Configuration for shared element transitions in [ScreenCommonHeader].
 *
 * This allows dynamic control over whether shared element transitions are enabled,
 * useful for scenarios like controlling animation based on navigation direction.
 *
 * @property leadingEnabled Whether the leading element should participate in shared transitions.
 * @property titleEnabled Whether the title element should participate in shared transitions.
 * @property actionsEnabled Whether the actions element should participate in shared transitions.
 */
@Immutable
data class ScreenCommonHeaderSharedElementConfig(
    val leadingEnabled: Boolean = true,
    val titleEnabled: Boolean = true,
    val actionsEnabled: Boolean = true,
) {
    companion object {
        /** All shared elements enabled (default). */
        val Enabled = ScreenCommonHeaderSharedElementConfig()

        /** All shared elements disabled. */
        val Disabled = ScreenCommonHeaderSharedElementConfig(
            leadingEnabled = false,
            titleEnabled = false,
            actionsEnabled = false,
        )
    }
}

/**
 * A common header component for screens with support for leading, title, and actions.
 *
 * The layout follows these rules:
 * - [leading] is always positioned at the start (left in LTR).
 * - [actions] is always positioned at the end (right in LTR).
 * - [title] is centered when possible. If centering would cause overlap with leading/actions
 *   (including minimum spacing), title is positioned immediately after leading with a
 *   constrained max width. The title composable should handle overflow (e.g., with ellipsis).
 *
 * ## Shared Element Transitions
 *
 * To enable shared element transitions between different ScreenCommonHeader instances,
 * provide [sharedTransitionScope] (or use [LocalScreenCommonHeaderSharedTransitionScope]).
 * The component handles animations internally using `AnimatedContent`.
 *
 * You can dynamically control which elements participate in the transition using
 * [sharedElementConfig]. This is useful for scenarios like controlling animation
 * based on navigation direction.
 *
 * Example using CompositionLocal (recommended):
 * ```kotlin
 * SharedTransitionLayout {
 *     CompositionLocalProvider(
 *         LocalScreenCommonHeaderSharedTransitionScope provides this
 *     ) {
 *         // All ScreenCommonHeader instances automatically participate in shared transitions
 *         ScreenCommonHeader(
 *             title = { Text("Title") },
 *             leading = { ScreenCommonHeaderBackButton(onClick = onBack) }
 *         )
 *     }
 * }
 * ```
 *
 * Example using explicit parameter:
 * ```kotlin
 * SharedTransitionLayout {
 *     ScreenCommonHeader(
 *         sharedTransitionScope = this,
 *         title = { Text("Title") }
 *     )
 * }
 * ```
 *
 * @param modifier Modifier to be applied to the header.
 * @param dimens Dimensions configuration for the header.
 * @param sharedTransitionScope Optional scope for shared element transitions.
 *        If not provided, falls back to [LocalScreenCommonHeaderSharedTransitionScope].
 * @param sharedElementConfig Configuration for which elements participate in shared transitions.
 * @param title Optional title content. Receives max width constraint when not centered.
 * @param leading Optional leading content (typically a back button).
 * @param actions Optional trailing actions.
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
@InteractTypeOnly(InteractType.Touch)
fun ScaffoldScope.ScreenCommonHeader(
    modifier: Modifier = Modifier,
    dimens: ScreenCommonHeaderDimens = ScreenCommonHeaderDefaults.dimens(),
    sharedTransitionScope: SharedTransitionScope? = LocalScreenCommonHeaderSharedTransitionScope.current,
    sharedElementConfig: ScreenCommonHeaderSharedElementConfig = ScreenCommonHeaderSharedElementConfig.Enabled,
    title: (@Composable () -> Unit)? = null,
    leading: (@Composable () -> Unit)? = null,
    actions: (@Composable RowScope.() -> Unit)? = null,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .hazeEffect(hazeState) {
                blurRadius = dimens.hazeBlurRadius
                progressive = HazeProgressive.verticalGradient(startIntensity = 1f, endIntensity = 0f)
            }
            .statusBarsPadding()
    ) {
        SubcomposeLayout { constraints ->
            val horizontalPaddingPx = dimens.horizontalPadding.roundToPx()
            val minSpacingPx = dimens.minSpacing.roundToPx()
            val heightPx = dimens.height.roundToPx()

            val availableWidth = constraints.maxWidth - horizontalPaddingPx * 2

            // 1. Measure leading with AnimatedContent for smooth transitions
            val leadingPlaceable = leading?.let {
                subcompose("leading") {
                    AnimatedContent(
                        targetState = leading,
                        transitionSpec = { fadeIn() togetherWith fadeOut() },
                        modifier = Modifier.height(dimens.height),
                    ) { targetLeading ->
                        Box(
                            modifier = Modifier
                                .height(dimens.height)
                                .then(
                                    if (sharedTransitionScope != null && sharedElementConfig.leadingEnabled) {
                                        with(sharedTransitionScope) {
                                            Modifier
                                                .sharedElement(
                                                    sharedContentState = rememberSharedContentState(
                                                        key = ScreenCommonHeaderSharedElementKey.LEADING
                                                    ),
                                                    animatedVisibilityScope = this@AnimatedContent,
                                                )
                                                .skipToLookaheadSize()
                                        }
                                    } else Modifier
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            targetLeading()
                        }
                    }
                }.firstOrNull()?.measure(
                    Constraints(maxWidth = availableWidth, maxHeight = heightPx)
                )
            }
            val leadingWidth = leadingPlaceable?.width ?: 0

            // 2. Measure actions with AnimatedContent
            val actionsPlaceable = actions?.let {
                subcompose("actions") {
                    AnimatedContent(
                        targetState = actions,
                        transitionSpec = { fadeIn() togetherWith fadeOut() },
                        modifier = Modifier.height(dimens.height),
                    ) { targetActions ->
                        Row(
                            modifier = Modifier
                                .height(dimens.height)
                                .then(
                                    if (sharedTransitionScope != null && sharedElementConfig.actionsEnabled) {
                                        with(sharedTransitionScope) {
                                            Modifier
                                                .sharedElement(
                                                    sharedContentState = rememberSharedContentState(
                                                        key = ScreenCommonHeaderSharedElementKey.ACTIONS
                                                    ),
                                                    animatedVisibilityScope = this@AnimatedContent,
                                                )
                                                .skipToLookaheadSize()
                                        }
                                    } else Modifier
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End,
                            content = targetActions
                        )
                    }
                }.firstOrNull()?.measure(
                    Constraints(maxWidth = availableWidth - leadingWidth - minSpacingPx, maxHeight = heightPx)
                )
            }
            val actionsWidth = actionsPlaceable?.width ?: 0

            // 3. Calculate title positioning
            val leadingOccupied = leadingWidth + (if (leadingWidth > 0) minSpacingPx else 0)
            val actionsOccupied = actionsWidth + (if (actionsWidth > 0) minSpacingPx else 0)

            val leftBoundary = horizontalPaddingPx + leadingOccupied
            val rightBoundary = constraints.maxWidth - horizontalPaddingPx - actionsOccupied
            val centerX = constraints.maxWidth / 2

            // Maximum width for centered title
            val maxCenteredWidth = 2 * minOf(centerX - leftBoundary, rightBoundary - centerX)

            // 4. Measure title with AnimatedContent for smooth transitions
            val titlePlaceable = title?.let {
                subcompose("title") {
                    AnimatedContent(
                        targetState = title,
                        transitionSpec = { fadeIn() togetherWith fadeOut() },
                        modifier = Modifier.height(dimens.height),
                    ) { targetTitle ->
                        Box(
                            modifier = Modifier
                                .height(dimens.height)
                                .then(
                                    if (sharedTransitionScope != null && sharedElementConfig.titleEnabled) {
                                        with(sharedTransitionScope) {
                                            Modifier
                                                .sharedElement(
                                                    sharedContentState = rememberSharedContentState(
                                                        key = ScreenCommonHeaderSharedElementKey.TITLE
                                                    ),
                                                    animatedVisibilityScope = this@AnimatedContent,
                                                )
                                                .skipToLookaheadSize()
                                        }
                                    } else Modifier
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            CompositionLocalProvider(
                                LocalTextStyle provides ZithianTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.SemiBold
                                )
                            ) {
                                targetTitle()
                            }
                        }
                    }
                }.firstOrNull()?.measure(
                    Constraints(
                        maxWidth = max(0, availableWidth - leadingOccupied - actionsOccupied),
                        maxHeight = heightPx
                    )
                )
            }
            val titleWidth = titlePlaceable?.width ?: 0

            // 5. Determine if title can be centered
            val canCenterTitle = maxCenteredWidth >= titleWidth

            // 6. Layout
            layout(constraints.maxWidth, heightPx) {
                // Place leading at start
                leadingPlaceable?.placeRelative(
                    x = horizontalPaddingPx,
                    y = 0
                )

                // Place title
                titlePlaceable?.let {
                    val titleX = if (canCenterTitle) {
                        (constraints.maxWidth - titleWidth) / 2
                    } else {
                        horizontalPaddingPx + leadingOccupied
                    }
                    it.placeRelative(x = titleX, y = 0)
                }

                // Place actions at end
                actionsPlaceable?.placeRelative(
                    x = constraints.maxWidth - horizontalPaddingPx - actionsWidth,
                    y = 0
                )
            }
        }
    }
}

/**
 * A pre-built back button with liquid effect for use in [ScreenCommonHeader.leading].
 *
 * This provides a consistent back button appearance with a chevron icon and liquid glass effect.
 *
 * @param onClick Callback when the back button is clicked.
 * @param modifier Modifier to be applied to the button.
 */
@Composable
fun ScaffoldScope.ScreenCommonHeaderBackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = ZithianTheme.colors.surfacePure.copy(.2f)
    Box(
        modifier = modifier
            .liquid(liquidState) {
                shape = CircleShape
                tint = backgroundColor
                frost = 0.dp
                refraction = 0.15f
                curve = 0.25f
                edge = 0.02f
                saturation = 1f
                dispersion = 0.26f
            }
            .clickable(onClick = onClick)
            .padding(5.dp)
    ) {
        CoilIcon(
            modifier = Modifier
                .rotate(180f)
                .size(27.dp)
                .rotate(180f),
            uri = ZithianResources.getUri("drawable/ic_chevron_left.svg"),
            contentDescription = null,
            tint = ZithianTheme.colors.text3,
        )
    }
}
