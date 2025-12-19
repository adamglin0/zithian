package com.adamglin.zithian.navigation3.decorator

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.LocalNavAnimatedContentScope
import com.adamglin.zithian.compose.scaffold.header.ProvideScreenCommonHeaderAnimatedVisibilityScope
import com.adamglin.zithian.navigation3.strategy.LocalIsOverlayScene

/**
 * A navigation3 decorator that automatically provides the [AnimatedVisibilityScope]
 * to ScreenCommonHeader instances within the screen.
 *
 * This decorator automatically detects [OverlayScene] environments (such as BottomSheet, Dialog)
 * via [LocalIsOverlayScene] and skips providing the scope in those contexts, since
 * [LocalNavAnimatedContentScope] is not available in overlay scenes.
 *
 * Example:
 * ```kotlin
 * NavDisplay(
 *     backStack = backStack,
 *     entryDecorators = listOf(
 *         rememberSaveableStateHolderNavEntryDecorator(),
 *         rememberScreenCommonHeaderNavEntryDecorator(),
 *     ),
 *     // ...
 * )
 * ```
 */
@Composable
fun rememberScreenCommonHeaderNavEntryDecorator() = NavEntryDecorator<NavKey> { entry ->
    if (!LocalIsOverlayScene.current) {
        ProvideScreenCommonHeaderAnimatedVisibilityScope(LocalNavAnimatedContentScope.current) {
            entry.Content()
        }
    } else {
        entry.Content()
    }
}
