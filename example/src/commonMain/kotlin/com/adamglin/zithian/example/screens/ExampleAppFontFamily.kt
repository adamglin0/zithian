package com.adamglin.zithian.example.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.FontResource
import zithian.example.generated.resources.Montserrat
import zithian.example.generated.resources.Res

val LocalExampleAppFontFamily =
    staticCompositionLocalOf<ExampleAppFontFamily> { error("No ExampleAppFontFamily provided") }

data class ExampleAppFontFamily(
    val montserrat: FontFamily,
)

@Composable
internal fun ExampleAppFontFamilyProvider(content: @Composable () -> Unit) {
    val montserrat = simpleVariableFontFamily(Res.font.Montserrat)
    CompositionLocalProvider(
        LocalExampleAppFontFamily provides ExampleAppFontFamily(
            montserrat = montserrat
        )
    ) {
        content()
    }
}

@Composable
private fun simpleVariableFontFamily(
    resource: FontResource
): FontFamily {
    return List(9) { (it + 1) * 100 }.map { weight ->
        Font(resource = resource, weight = FontWeight(weight))
    }.let { fonts -> FontFamily(*fonts.toTypedArray()) }
}