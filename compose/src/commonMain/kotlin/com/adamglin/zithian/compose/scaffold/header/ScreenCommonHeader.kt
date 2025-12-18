package com.adamglin.zithian.compose.scaffold.header

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.LocalNavAnimatedContentScope
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
 *         NavHost(...) {
 *             composable(...) {
 *                 CompositionLocalProvider(
 *                     LocalScreenCommonHeaderAnimatedVisibilityScope provides this
 *                 ) {
 *                     // All ScreenCommonHeader instances will automatically participate in shared transitions
 *                     ScreenCommonHeader(title = { Text("Title") })
 *                 }
 *             }
 *         }
 *     }
 * }
 */
@Suppress("CompositionLocalAllowlist")
val LocalScreenCommonHeaderSharedTransitionScope = staticCompositionLocalOf<SharedTransitionScope?> { null }

/**
 * CompositionLocal for providing [AnimatedVisibilityScope] to [ScreenCommonHeader].
 *
 * This is required for shared element transitions to work correctly between screens.
 * Usually provided from within a [NavHost] composable.
 */
@Suppress("CompositionLocalAllowlist")
val LocalScreenCommonHeaderAnimatedVisibilityScope = staticCompositionLocalOf<AnimatedVisibilityScope?> { null }

/**
 * Helper to provide the [AnimatedVisibilityScope] to [ScreenCommonHeader] and its children.
 *
 * Call this inside your navigation destination (e.g., inside `composable` or `entry`) to
 * enable shared element transitions between screens.
 *
 * Example:
 * ```kotlin
 * NavHost(...) {
 *     composable("home") { // `this` is AnimatedVisibilityScope (AnimatedContentScope)
 *         ProvideScreenCommonHeaderAnimatedVisibilityScope(this) {
 *             HomeScreen()
 *         }
 *     }
 * }
 * ```
 */
@Composable
fun ProvideScreenCommonHeaderAnimatedVisibilityScope(
    scope: AnimatedVisibilityScope,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalScreenCommonHeaderAnimatedVisibilityScope provides scope,
        content = content
    )
}

/**
 * A navigation3 decorator that automatically provides the [AnimatedVisibilityScope]
 * to [ScreenCommonHeader] instances within the screen.
 *
 * Add this to your [NavDisplay]'s `entryDecorators` to enable shared element
 * transitions without manual boilerplate in each screen.
 */
@Composable
fun rememberScreenCommonHeaderNavEntryDecorator() = NavEntryDecorator<NavKey> { entry ->
    val animatedContentScope = LocalNavAnimatedContentScope.current
    ProvideScreenCommonHeaderAnimatedVisibilityScope(animatedContentScope) {
        entry.Content()
    }
}

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
 * provide [sharedTransitionScope] (or use [LocalScreenCommonHeaderSharedTransitionScope])
 * and ensure [LocalScreenCommonHeaderAnimatedVisibilityScope] is provided (e.g., via
 * [rememberScreenCommonHeaderNavEntryDecorator]).
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
    title: (@Composable () -> Unit)? = null,
    leading: (@Composable () -> Unit)? = null,
    actions: (@Composable RowScope.() -> Unit)? = null,
) {
    val animatedVisibilityScope = LocalScreenCommonHeaderAnimatedVisibilityScope.current
    Box(
        modifier = modifier
            .fillMaxWidth()
            .hazeEffect(hazeState) {
                blurRadius = dimens.hazeBlurRadius
                progressive = HazeProgressive.verticalGradient(startIntensity = 1f, endIntensity = 0f)
            }
            .statusBarsPadding()
            .height(dimens.height)
            .padding(horizontal = dimens.horizontalPadding)
    ) {
        // Leading - aligned to start
        if (leading != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .then(
                        if (sharedTransitionScope != null && animatedVisibilityScope != null) {
                            with(sharedTransitionScope) {
                                Modifier
                                    .sharedElement(
                                        sharedContentState = rememberSharedContentState(
                                            key = ScreenCommonHeaderSharedElementKey.LEADING
                                        ),
                                        animatedVisibilityScope = animatedVisibilityScope,
                                    )
                                    .skipToLookaheadSize()
                            }
                        } else Modifier
                    ),
                contentAlignment = Alignment.Center
            ) {
                leading()
            }
        }

        // Title - centered
        if (title != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .then(
                        if (sharedTransitionScope != null && animatedVisibilityScope != null) {
                            with(sharedTransitionScope) {
                                Modifier
                                    .sharedElement(
                                        sharedContentState = rememberSharedContentState(
                                            key = ScreenCommonHeaderSharedElementKey.TITLE
                                        ),
                                        animatedVisibilityScope = animatedVisibilityScope,
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
                    title()
                }
            }
        }

        // Actions - aligned to end
        if (actions != null) {
            Row(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .then(
                        if (sharedTransitionScope != null && animatedVisibilityScope != null) {
                            with(sharedTransitionScope) {
                                Modifier
                                    .sharedElement(
                                        sharedContentState = rememberSharedContentState(
                                            key = ScreenCommonHeaderSharedElementKey.ACTIONS
                                        ),
                                        animatedVisibilityScope = animatedVisibilityScope,
                                    )
                                    .skipToLookaheadSize()
                            }
                        } else Modifier
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                content = actions
            )
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
