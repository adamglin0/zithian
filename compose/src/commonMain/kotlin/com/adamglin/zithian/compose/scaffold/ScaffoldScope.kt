package com.adamglin.zithian.compose.scaffold

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.rememberHazeState
import io.github.fletchmckee.liquid.LiquidState
import io.github.fletchmckee.liquid.rememberLiquidState

@Stable
class ScaffoldScope internal constructor(
    val hazeState: HazeState,
    val liquidState: LiquidState,
) {
    private var _headerHeight by mutableStateOf(0.dp)
    var headerHeight: Dp
        get() = _headerHeight
        internal set(value) {
            _headerHeight = value
        }

    private var _bottomHeight by mutableStateOf(0.dp)
    var bottomHeight: Dp
        get() = _bottomHeight
        internal set(value) {
            _bottomHeight = value
        }
}

@Composable
fun rememberScaffoldScope(): ScaffoldScope {
    val hazeState = rememberHazeState()
    val liquidState = rememberLiquidState()
    return remember {
        ScaffoldScope(hazeState, liquidState)
    }
}