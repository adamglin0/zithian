package com.adamglin.zithian.compose.scaffold

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import com.adamglin.zithian.compose.theme.ZithianTheme
import dev.chrisbanes.haze.hazeSource
import io.github.fletchmckee.liquid.liquefiable

@Composable
fun BasicScaffold(
    modifier: Modifier = Modifier,
    header: (@Composable ScaffoldScope.() -> Unit)? = null,
    bottom: (@Composable ScaffoldScope.() -> Unit)? = null,
    backgroundColor: Color = ZithianTheme.colors.background,
    content: @Composable ScaffoldScope.() -> Unit,
) {
    val headerNotNull = header ?: {}
    val bottomNotNull = bottom ?: {}
    Box(
        modifier = modifier.background(backgroundColor)
    ) {
        val screenScaffoldScope = rememberScaffoldScope()
        if (header == null && bottom == null) {
            ContentWrapper(screenScaffoldScope, backgroundColor) {
                content()
            }
            return@Box
        }
        SubcomposeLayout(Modifier) { constraints ->
            val headerPlaceables = subcompose("header") {
                headerNotNull(screenScaffoldScope)
            }.map {
                it.measure(constraints)
            }
            val headerHeight = headerPlaceables.maxOfOrNull { it.height } ?: 0
            screenScaffoldScope.headerHeight = headerHeight.toDp()
            val contentPlaceables = subcompose("content") {
                ContentWrapper(screenScaffoldScope, backgroundColor) {
                    content()
                }
            }.map { it.measure(constraints) }
            val bottomPlaceables = subcompose("bottom") {
                bottomNotNull(screenScaffoldScope)
            }.map {
                it.measure(constraints)
            }
            val bottomHeight = bottomPlaceables.maxOfOrNull { it.height } ?: 0
            val contentHeight = contentPlaceables.maxOfOrNull { it.height } ?: 0
            layout(constraints.maxWidth, maxOf(headerHeight + bottomHeight, contentHeight)) {
                contentPlaceables.forEach { it.placeRelative(0, 0) }
                headerPlaceables.forEach { it.placeRelative(0, 0) }
                bottomPlaceables.forEach {
                    it.placeRelative(0, constraints.maxHeight - bottomHeight)
                }
            }
        }
    }
}

@Composable
private fun ContentWrapper(
    screenScaffoldScope: ScaffoldScope,
    backgroundColor: Color,
    content: @Composable ScaffoldScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .hazeSource(screenScaffoldScope.hazeState)
            .background(backgroundColor)
            .liquefiable(screenScaffoldScope.liquidState),
    ) {
        content(screenScaffoldScope)
    }
}