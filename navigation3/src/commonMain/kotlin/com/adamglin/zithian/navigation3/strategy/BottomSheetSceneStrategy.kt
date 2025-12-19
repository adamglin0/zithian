package com.adamglin.zithian.navigation3.strategy

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.scene.OverlayScene
import androidx.navigation3.scene.Scene
import androidx.navigation3.scene.SceneStrategy
import androidx.navigation3.scene.SceneStrategyScope

/**
 * CompositionLocal 用于标记当前是否处于 OverlayScene 中。
 * 
 * 在 OverlayScene（如 BottomSheet、Dialog 等）中，[LocalNavAnimatedContentScope] 不可用，
 * 因为 OverlayScene 的内容是在 AnimatedContent 外部渲染的。
 * 
 * 使用此 local 可以安全地检测是否应该访问 [LocalNavAnimatedContentScope]。
 */
@Suppress("CompositionLocalAllowlist")
val LocalIsOverlayScene = staticCompositionLocalOf { false }

internal class BottomSheetScene<T : Any>(
    override val key: T,
    override val previousEntries: List<NavEntry<T>>,
    override val overlaidEntries: List<NavEntry<T>>,
    private val entry: NavEntry<T>,
) : OverlayScene<T> {

    override val entries: List<NavEntry<T>> = listOf(entry)

    override val content: @Composable (() -> Unit) = {
        CompositionLocalProvider(
            LocalIsOverlayScene provides true
        ) {
            entry.Content()
        }
    }
}

class BottomSheetSceneStrategy<T : Any> : SceneStrategy<T> {
    override fun SceneStrategyScope<T>.calculateScene(entries: List<NavEntry<T>>): Scene<T>? {
        val lastEntry = entries.lastOrNull()
        val isVisible = lastEntry?.metadata?.get(BOTTOM_SHEET_KEY) == true
        if (!isVisible) return null
        @Suppress("UNCHECKED_CAST")
        return BottomSheetScene(
            key = lastEntry.contentKey as T,
            previousEntries = entries.dropLast(1),
            overlaidEntries = entries.dropLast(1),
            entry = lastEntry,
        )
    }

    companion object {
        fun bottomSheet(): Map<String, Any> = mapOf(BOTTOM_SHEET_KEY to true)

        internal const val BOTTOM_SHEET_KEY = "BOTTOM_SHEET_KEY"
    }
}