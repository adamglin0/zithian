package androidx.compose.ui

import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.platform.PlatformWindowInsets
import androidx.compose.ui.platform.PlatformWindowInsetsProviderNode

@OptIn(InternalComposeUiApi::class)
internal fun Modifier.overridePlatformWindowInsets(customInsets: PlatformWindowInsets): Modifier = this.then(
    WindowInsetsOverrideElement(customInsets)
)


@OptIn(InternalComposeUiApi::class)
private data class WindowInsetsOverrideElement(
    val customInsets: PlatformWindowInsets
) : ModifierNodeElement<WindowInsetsOverrideNode>() {
    override fun create() = WindowInsetsOverrideNode(customInsets)
    override fun update(node: WindowInsetsOverrideNode) {
        node.customInsets = customInsets
        node.updateInsets()
    }
}

@OptIn(InternalComposeUiApi::class)
private class WindowInsetsOverrideNode(
    var customInsets: PlatformWindowInsets
) : PlatformWindowInsetsProviderNode() {

    fun updateInsets() {
        windowInsetsInvalidated()
    }

    override fun calculatePlatformInsets(ancestorWindowInsets: PlatformWindowInsets): PlatformWindowInsets {
        return customInsets
    }
}