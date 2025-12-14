package com.adamglin.zithian.compose.scaffold

import androidx.compose.runtime.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.rememberHazeState
import io.github.fletchmckee.liquid.LiquidState
import io.github.fletchmckee.liquid.rememberLiquidState

@Stable
interface ScaffoldScope {
    val hazeState: HazeState
    val liquidState: LiquidState
    var headerHeight: Dp
    var bottomHeight: Dp
}

@Stable
internal class ScaffoldScopeImpl(
    override val hazeState: HazeState,
    override val liquidState: LiquidState,
) : ScaffoldScope {
    private var _headerHeight by mutableStateOf(0.dp)
    override var headerHeight: Dp
        get() = _headerHeight
        set(value) {
            _headerHeight = value
        }

    private var _bottomHeight by mutableStateOf(0.dp)
    override var bottomHeight: Dp
        get() = _bottomHeight
        set(value) {
            _bottomHeight = value
        }
}

@Composable
fun rememberScaffoldScope(): ScaffoldScope {
    val hazeState = rememberHazeState()
    val liquidState = rememberLiquidState()
    return remember {
        ScaffoldScopeImpl(hazeState, liquidState)
    }
}