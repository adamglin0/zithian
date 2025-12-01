package com.adamglin.zithian.compose.dropdown

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

interface DropdownMenuAnchorScope {
    fun current(content: @Composable DropdownMenuAnchorStateScope.() -> Unit)
    fun menu(content: @Composable DropdownMenuAnchorStateScope.() -> Unit)
}

interface DropdownMenuAnchorStateScope {
    var isMenuVisible: Boolean
}

private class PrivateDropdownMenuAnchorScope() : DropdownMenuAnchorScope, DropdownMenuAnchorStateScope {
    private val _isMenuVisible = mutableStateOf(false)
    override var isMenuVisible: Boolean
        get() = _isMenuVisible.value
        set(value) {
            _isMenuVisible.value = value
        }
    var currentContent: @Composable (DropdownMenuAnchorStateScope.() -> Unit)? = null
        private set
    var menuContent: @Composable (DropdownMenuAnchorStateScope.() -> Unit)? = null
        private set

    override fun current(content: @Composable (DropdownMenuAnchorStateScope.() -> Unit)) {
        currentContent = content
    }

    override fun menu(content: @Composable (DropdownMenuAnchorStateScope.() -> Unit)) {
        menuContent = content
    }
}

@Composable
fun DropdownMenuAnchor(
    modifier: Modifier = Modifier,
    content: @Composable DropdownMenuAnchorScope.() -> Unit,
) {
    val scope = remember {
        PrivateDropdownMenuAnchorScope()
    }
    content(scope)
    Column(modifier = modifier) {
        scope.currentContent?.invoke(scope)
        Box {
            if (scope.isMenuVisible) scope.menuContent?.invoke(scope)
        }
    }
}