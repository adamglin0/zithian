package com.adamglin.zithian.compose.dialog

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun rememberDialogState(
    onDismissRequest: () -> Unit,
): DialogState {
    val coroutineScope = rememberCoroutineScope()
    return remember { DialogState(onDismissRequest, coroutineScope) }
}

@Stable
class DialogState internal constructor(
    internal val onDismissRequest: () -> Unit,
    internal val coroutineScope: CoroutineScope,
    internal val exitDelay: Long = 300,
) {
    val mutableTransitionState = MutableTransitionState(false).apply {
        targetState = true
    }

    fun closeWithAnimation() {
        coroutineScope.launch {
            mutableTransitionState.targetState = false
            delay(exitDelay)
            onDismissRequest()
        }
    }
}
