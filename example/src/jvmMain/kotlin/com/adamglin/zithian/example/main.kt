package com.adamglin.zithian.example

import androidx.compose.runtime.Composer
import androidx.compose.runtime.ExperimentalComposeRuntimeApi
import androidx.compose.runtime.tooling.ComposeStackTraceMode
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalComposeRuntimeApi::class)
fun main() {
    Composer.setDiagnosticStackTraceMode(ComposeStackTraceMode.SourceInformation)
    runBlocking {
//        launch {
//            pointerApplication()
//        }
        touchApplication()
    }
}