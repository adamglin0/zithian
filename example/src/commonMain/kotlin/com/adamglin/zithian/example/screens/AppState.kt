package com.adamglin.zithian.example.screens

import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation3.runtime.NavKey
import com.adamglin.zithian.example.screens.features.components.ComponentsNavKey
import com.adamglin.zithian.example.screens.features.config.ConfigNavKey
import com.adamglin.zithian.example.screens.features.theme.ThemeNavKey
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toPersistentList

val LocalAppState = staticCompositionLocalOf<AppState> { error("No AppState provided") }

interface TopLevelNavKey : NavKey

@Stable
class AppState(initialBackstack: List<NavKey>) {
    private val _backstack = mutableStateOf(
        value = persistentListOf(*initialBackstack.toTypedArray())
    )

    val backstack: PersistentList<NavKey> get() = _backstack.value
    private val topLevelNavKeys = persistentSetOf(
        ThemeNavKey,
        ComponentsNavKey,
        ConfigNavKey
    )

    fun navigate(block: MutableList<NavKey>.() -> Unit) {
        _backstack.value = _backstack.value.toMutableList()
            .apply { block() }
            .toPersistentList()
    }
}