@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.dokka)
    alias(libs.plugins.mavenPublish)
    alias(libs.plugins.binaryCompatibilityValidator)
}

group = "com.adamglin.zithian"

version = "1.0.0"

kotlin {
    jvm {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_21
        }
    }

    js(compiler = IR) {
        browser()
    }

    wasmJs {
        browser()
    }

    sourceSets {
        val webMain = create("webMain")
        webMain.dependsOn(commonMain.get())
        jsMain.configure { dependsOn(webMain) }
        wasmJsMain.configure { dependsOn(webMain) }
        commonMain.dependencies {
            // compose
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            // other
            implementation(projects.zithian.compose)
            implementation(libs.haze)
            implementation(libs.liquid)
            api(libs.composeContinuousRoundedCornerShape)
            implementation(libs.coil.compose)
            implementation(libs.coil.svg)
            api(libs.kotlinx.collections.immutable)
            api(libs.jbr.api)
        }
        all {
            languageSettings {
                optIn("kotlinx.cinterop.ExperimentalForeignApi")
                optIn("kotlin.time.kotlin.time")
            }
        }
    }
    compilerOptions {
        freeCompilerArgs.add("-Xcontext-parameters")
    }
}

compose {
    resources {
        publicResClass = true
        generateResClass = always
        nameOfResClass = "EmulatorResources"
    }
}