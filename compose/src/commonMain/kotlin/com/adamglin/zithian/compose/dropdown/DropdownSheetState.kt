package com.adamglin.zithian.compose.dropdown

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

@Stable
class DropdownSheetState(
    initialExpanded: Boolean = false,
) {
    var expanded by mutableStateOf(initialExpanded)
        private set

    fun show() {
        expanded = true
    }

    fun hide() {
        expanded = false
    }

    fun toggle() {
        expanded = !expanded
    }
}