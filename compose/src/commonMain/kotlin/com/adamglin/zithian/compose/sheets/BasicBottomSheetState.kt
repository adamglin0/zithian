package com.adamglin.zithian.compose.sheets

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Creates and remembers a [BasicBottomSheetState] for internal use.
 */
@Composable
internal fun rememberBasicBottomSheetState(
    onDismissRequest: () -> Unit,
): BasicBottomSheetState {
    val coroutineScope = rememberCoroutineScope()
    return remember { BasicBottomSheetState(onDismissRequest, coroutineScope) }
}

/**
 * Internal state holder for [BasicBottomSheet] that manages animation and dismissal logic.
 *
 * This state uses [MutableTransitionState] to track visibility, and handles
 * dismissal through coroutines with [invokeOnCompletion] to ensure the
 * callback is only called after the exit animation finishes.
 */
@Stable
internal class BasicBottomSheetState(
    private val onDismissRequest: () -> Unit,
    private val coroutineScope: CoroutineScope,
) {
    /**
     * The transition state that drives the sheet's enter/exit animations.
     * Initialized with targetState = true to trigger enter animation immediately.
     */
    val transitionState = MutableTransitionState(false).apply {
        targetState = true
    }

    /**
     * Whether dismiss has already been initiated. Prevents multiple calls.
     */
    private var dismissing = false

    /**
     * Initiates the close animation and calls [onDismissRequest] upon completion.
     * Multiple calls are ignored once dismiss has been initiated.
     */
    fun dismiss() {
        if (dismissing) return
        dismissing = true

        coroutineScope.launch {
            transitionState.targetState = false
            while (transitionState.currentState != transitionState.targetState) {
                kotlinx.coroutines.delay(16)
            }
        }.invokeOnCompletion {
            onDismissRequest()
        }
    }
}

/**
 * Scope interface for bottom sheet content, providing access to the [dismiss] function.
 */
@Stable
interface BottomSheetScope {
    /**
     * Initiates the close animation and triggers dismissal after animation completes.
     */
    fun dismiss()
}

internal class BottomSheetScopeImpl(
    private val state: BasicBottomSheetState
) : BottomSheetScope {
    override fun dismiss() = state.dismiss()
}

