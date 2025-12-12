package com.adamglin.zithian.compose.navigation

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.adamglin.composecontinuousroundedcornershape.ContinuousRoundedCornerShape
import com.adamglin.zithian.compose.text.LocalTextStyle
import com.adamglin.zithian.compose.theme.InteractType
import com.adamglin.zithian.compose.theme.LocalContentColor
import com.adamglin.zithian.compose.theme.LocalInteractType
import com.adamglin.zithian.compose.theme.ZithianTheme
import com.adamglin.zithian.compose.utils.interactPointer

/**
 * Scope for building items within a [SimpleBottomNavigation].
 *
 * Provides [selectedIndex] to check which item is currently selected,
 * and methods to add navigation items.
 */
interface SimpleBottomNavigationScope {
    /**
     * The index of the currently selected item.
     */
    val selectedIndex: Int

    /**
     * Adds a single item to the navigation bar.
     *
     * @param key A stable and unique key representing the item. Using the same key
     *   for multiple items is not allowed. Type of the key should be saveable
     *   via Bundle on Android. If null is passed the position in the list will represent the key.
     * @param content The content of the item, receiving [SimpleBottomNavigationItemScope]
     *   which provides the item's [index][SimpleBottomNavigationItemScope.index],
     *   [selected][SimpleBottomNavigationItemScope.selected] state, and
     *   [select][SimpleBottomNavigationItemScope.select] callback.
     */
    fun item(
        key: Any? = null,
        content: @Composable SimpleBottomNavigationItemScope.() -> Unit
    )

    /**
     * Adds [count] items to the navigation bar.
     *
     * @param count The number of items to add.
     * @param key A factory of stable and unique keys representing the item. Using the same key
     *   for multiple items is not allowed. Type of the key should be saveable
     *   via Bundle on Android. If null is passed the position in the list will represent the key.
     * @param itemContent The content displayed by a single item, receiving
     *   [SimpleBottomNavigationItemScope] and the local index within this items block.
     */
    fun items(
        count: Int,
        key: ((index: Int) -> Any)? = null,
        itemContent: @Composable SimpleBottomNavigationItemScope.(localIndex: Int) -> Unit
    )
}

/**
 * Scope provided to each navigation item's content.
 *
 * Provides information about the item's position and selection state,
 * and a callback to select this item.
 */
@Stable
interface SimpleBottomNavigationItemScope {
    /**
     * The global index of this item within the navigation bar.
     */
    val index: Int

    /**
     * Whether this item is currently selected.
     */
    val selected: Boolean

    /**
     * Selects this item by calling [SimpleBottomNavigation]'s onSelectedIndexChange.
     */
    fun select()
}

/**
 * Adds a list of items to the navigation bar.
 *
 * @param items The data list.
 * @param key A factory of stable and unique keys representing the item. Using the same key
 *   for multiple items is not allowed. Type of the key should be saveable
 *   via Bundle on Android. If null is passed the position in the list will represent the key.
 * @param itemContent The content displayed by a single item.
 */
inline fun <T> SimpleBottomNavigationScope.items(
    items: List<T>,
    noinline key: ((item: T) -> Any)? = null,
    crossinline itemContent: @Composable SimpleBottomNavigationItemScope.(item: T) -> Unit
) = items(
    count = items.size,
    key = if (key != null) { index: Int -> key(items[index]) } else null,
    itemContent = { index -> itemContent(items[index]) }
)

/**
 * Adds an array of items to the navigation bar.
 *
 * @param items The data array.
 * @param key A factory of stable and unique keys representing the item. Using the same key
 *   for multiple items is not allowed. Type of the key should be saveable
 *   via Bundle on Android. If null is passed the position in the list will represent the key.
 * @param itemContent The content displayed by a single item.
 */
inline fun <T> SimpleBottomNavigationScope.items(
    items: Array<T>,
    noinline key: ((item: T) -> Any)? = null,
    crossinline itemContent: @Composable SimpleBottomNavigationItemScope.(item: T) -> Unit
) = items(
    count = items.size,
    key = if (key != null) { index: Int -> key(items[index]) } else null,
    itemContent = { index -> itemContent(items[index]) }
)

/**
 * Adds a list of items to the navigation bar where the content receives index.
 *
 * @param items The data list.
 * @param key A factory of stable and unique keys representing the item. Using the same key
 *   for multiple items is not allowed. Type of the key should be saveable
 *   via Bundle on Android. If null is passed the position in the list will represent the key.
 * @param itemContent The content displayed by a single item with its index.
 */
inline fun <T> SimpleBottomNavigationScope.itemsIndexed(
    items: List<T>,
    noinline key: ((index: Int, item: T) -> Any)? = null,
    crossinline itemContent: @Composable SimpleBottomNavigationItemScope.(index: Int, item: T) -> Unit
) = items(
    count = items.size,
    key = if (key != null) { index: Int -> key(index, items[index]) } else null,
    itemContent = { index -> itemContent(index, items[index]) }
)

private class SimpleBottomNavigationInterval(
    val key: ((index: Int) -> Any)?,
    val content: @Composable SimpleBottomNavigationItemScope.(localIndex: Int) -> Unit
)

private class SimpleBottomNavigationScopeImpl(
    override val selectedIndex: Int,
    private val onSelectedIndexChange: (Int) -> Unit
) : SimpleBottomNavigationScope {
    private val _intervals = mutableListOf<Pair<Int, SimpleBottomNavigationInterval>>()
    private var _size = 0

    val size: Int get() = _size

    fun getKey(index: Int): Any {
        val (startIndex, interval) = findInterval(index)
        val localIndex = index - startIndex
        return interval.key?.invoke(localIndex) ?: index
    }

    fun getContent(index: Int): @Composable () -> Unit {
        val (startIndex, interval) = findInterval(index)
        val localIndex = index - startIndex
        val itemScope = SimpleBottomNavigationItemScopeImpl(
            index = index,
            selected = index == selectedIndex,
            onSelect = { onSelectedIndexChange(index) }
        )
        return { interval.content(itemScope, localIndex) }
    }

    private fun findInterval(index: Int): Pair<Int, SimpleBottomNavigationInterval> {
        var currentStart = 0
        for ((count, interval) in _intervals) {
            if (index < currentStart + count) {
                return currentStart to interval
            }
            currentStart += count
        }
        error("Index $index out of bounds for size $_size")
    }

    override fun item(
        key: Any?,
        content: @Composable SimpleBottomNavigationItemScope.() -> Unit
    ) {
        _intervals.add(
            1 to SimpleBottomNavigationInterval(
                key = if (key != null) { _: Int -> key } else null,
                content = { content() }
            )
        )
        _size++
    }

    override fun items(
        count: Int,
        key: ((index: Int) -> Any)?,
        itemContent: @Composable SimpleBottomNavigationItemScope.(localIndex: Int) -> Unit
    ) {
        _intervals.add(
            count to SimpleBottomNavigationInterval(
                key = key,
                content = itemContent
            )
        )
        _size += count
    }
}

@Stable
private class SimpleBottomNavigationItemScopeImpl(
    override val index: Int,
    override val selected: Boolean,
    private val onSelect: () -> Unit
) : SimpleBottomNavigationItemScope {
    override fun select() = onSelect()
}

@Immutable
data class SimpleBottomNavigationDimens(
    val contentPadding: PaddingValues,
    val height: Dp,
    val itemSpacing: Dp,
    val indicatorCornerRadius: Dp,
    val iconSize: Dp,
    val indicatorPadding: PaddingValues,
) {
    companion object {
        internal val Pointer = SimpleBottomNavigationDimens(
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
            height = 64.dp,
            itemSpacing = 8.dp,
            indicatorCornerRadius = 12.dp,
            iconSize = 24.dp,
            indicatorPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        )

        internal val Touch = SimpleBottomNavigationDimens(
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 15.dp),
            height = 80.dp,
            itemSpacing = 0.dp,
            indicatorCornerRadius = 16.dp,
            iconSize = 24.dp,
            indicatorPadding = PaddingValues(horizontal = 20.dp, vertical = 4.dp)
        )

        fun of(interactType: InteractType) = when (interactType) {
            InteractType.Pointer -> Pointer
            InteractType.Touch -> Touch
        }
    }
}

@Immutable
data class SimpleBottomNavigationColors(
    val backgroundColor: Color,
    val contentColor: Color,
    val selectedContentColor: Color,
    val indicatorColor: Color,
    val selectedIndicatorColor: Color,
)

object SimpleBottomNavigationDefaults {
    @Composable
    fun dimens(type: InteractType = LocalInteractType.current) = SimpleBottomNavigationDimens.of(type)

    @Composable
    fun colors(
        backgroundColor: Color = ZithianTheme.colors.surface,
        contentColor: Color = ZithianTheme.colors.text8,
        selectedContentColor: Color = ZithianTheme.colors.primary,
        indicatorColor: Color = Color.Transparent,
        selectedIndicatorColor: Color = ZithianTheme.colors.primary.copy(alpha = 0.1f),
    ) = SimpleBottomNavigationColors(
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        selectedContentColor = selectedContentColor,
        indicatorColor = indicatorColor,
        selectedIndicatorColor = selectedIndicatorColor,
    )
}

/**
 * A simple bottom navigation bar that displays a row of navigation items.
 *
 * This component manages the selection state through [selectedIndex] and [onSelectedIndexChange],
 * providing a clean API for building navigation with minimal boilerplate.
 *
 * Example usage:
 * ```
 * var selectedIndex by remember { mutableStateOf(0) }
 *
 * SimpleBottomNavigation(
 *     selectedIndex = selectedIndex,
 *     onSelectedIndexChange = { selectedIndex = it }
 * ) {
 *     items(tabs) { tab ->
 *         SimpleBottomNavigationItem(
 *             selected = selected,
 *             onClick = ::select,
 *             icon = { Icon(tab.icon) },
 *             label = { Text(tab.label) }
 *         )
 *     }
 * }
 * ```
 *
 * @param selectedIndex The index of the currently selected item.
 * @param onSelectedIndexChange Callback invoked when the user selects an item.
 * @param modifier The modifier to apply to this layout.
 * @param dimens The dimensions configuration for the navigation bar.
 * @param colors The colors configuration for the navigation bar.
 * @param content The content of the navigation bar, defined using [SimpleBottomNavigationScope].
 */
@Composable
fun SimpleBottomNavigation(
    selectedIndex: Int,
    onSelectedIndexChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    dimens: SimpleBottomNavigationDimens = SimpleBottomNavigationDefaults.dimens(),
    colors: SimpleBottomNavigationColors = SimpleBottomNavigationDefaults.colors(),
    content: SimpleBottomNavigationScope.() -> Unit,
) {
    val scope = SimpleBottomNavigationScopeImpl(selectedIndex, onSelectedIndexChange).apply(content)

    Row(
        modifier = modifier
            .background(colors.backgroundColor)
            .padding(dimens.contentPadding)
            .navigationBarsPadding(),
        horizontalArrangement = Arrangement.spacedBy(dimens.itemSpacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(scope.size) { index ->
            key(scope.getKey(index)) {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    scope.getContent(index).invoke()
                }
            }
        }
    }
}

/**
 * An icon-only item for [SimpleBottomNavigation].
 *
 * This item displays only an icon without any background indicator or label.
 * When selected, it shows the theme's primary color; when not selected,
 * it appears semi-transparent.
 *
 * Example usage within [SimpleBottomNavigationScope]:
 * ```
 * SimpleBottomNavigationIconItem(
 *     selected = selected,
 *     onClick = ::select,
 *     icon = { Icon(outlineIcon) },
 *     selectedIcon = { Icon(filledIcon) }
 * )
 * ```
 *
 * @param selected Whether this item is currently selected.
 * @param onClick Called when this item is clicked.
 * @param icon The icon to display when not selected.
 * @param selectedIcon The icon to display when selected.
 * @param modifier The modifier to apply to this item.
 * @param enabled Whether this item is enabled.
 * @param interactionSource The interaction source for this item.
 * @param colors The colors configuration for this item.
 * @param dimens The dimensions configuration for this item.
 */
@Composable
fun SimpleBottomNavigationIconItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    selectedIcon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: SimpleBottomNavigationColors = SimpleBottomNavigationDefaults.colors(),
    dimens: SimpleBottomNavigationDimens = SimpleBottomNavigationDefaults.dimens(),
) {
    val currentIcon = if (selected) selectedIcon else icon
    val interactType = LocalInteractType.current

    val contentColor = if (selected) colors.selectedContentColor else colors.contentColor

    Box(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current,
                enabled = enabled,
                onClick = onClick,
                role = Role.Tab
            )
            .interactPointer(interactType, enabled),
        contentAlignment = Alignment.Center
    ) {
        CompositionLocalProvider(LocalContentColor provides contentColor) {
            Box(modifier = Modifier.size(dimens.iconSize)) {
                currentIcon()
            }
        }
    }
}

/**
 * A single item in a [SimpleBottomNavigation].
 *
 * When used within a [SimpleBottomNavigationScope], you can use the scope's
 * [selected][SimpleBottomNavigationItemScope.selected] and
 * [select][SimpleBottomNavigationItemScope.select] for a cleaner API:
 *
 * ```
 * SimpleBottomNavigationItem(
 *     selected = selected,  // from scope
 *     onClick = ::select,   // from scope
 *     icon = { Icon(imageVector) }
 * )
 * ```
 *
 * @param selected Whether this item is currently selected.
 * @param onClick Called when this item is clicked.
 * @param modifier The modifier to apply to this item.
 * @param icon The icon to display when not selected.
 * @param selectedIcon The icon to display when selected. Defaults to [icon] if not provided.
 * @param label Optional label to display below the icon.
 * @param enabled Whether this item is enabled.
 * @param interactionSource The interaction source for this item.
 * @param colors The colors configuration for this item.
 * @param dimens The dimensions configuration for this item.
 * @param textStyle The text style for the label.
 */
@Composable
fun SimpleBottomNavigationItem(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    selectedIcon: (@Composable () -> Unit)? = null,
    label: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: SimpleBottomNavigationColors = SimpleBottomNavigationDefaults.colors(),
    dimens: SimpleBottomNavigationDimens = SimpleBottomNavigationDefaults.dimens(),
    textStyle: TextStyle = ZithianTheme.typography.bodySmall,
) {
    val currentIcon = when {
        selected && selectedIcon != null -> selectedIcon
        icon != null -> icon
        else -> null
    }
    val interactType = LocalInteractType.current

    val contentColor = if (selected) colors.selectedContentColor else colors.contentColor
    val indicatorColor = if (selected) colors.selectedIndicatorColor else colors.indicatorColor

    Column(
        modifier = modifier
            .clip(ContinuousRoundedCornerShape(dimens.indicatorCornerRadius))
            .background(indicatorColor)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                onClick = onClick,
                role = Role.Tab
            )
            .interactPointer(interactType, enabled)
            .padding(dimens.indicatorPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CompositionLocalProvider(LocalContentColor provides contentColor) {
            if (currentIcon != null) {
                Box(modifier = Modifier.size(dimens.iconSize)) {
                    currentIcon()
                }
            }

            if (label != null) {
                CompositionLocalProvider(LocalTextStyle provides textStyle) {
                    label()
                }
            }
        }
    }
}
