@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.dokka)
    alias(libs.plugins.mavenPublish)
    alias(libs.plugins.binaryCompatibilityValidator)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_1_8
        }
    }
    jvm {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_1_8
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    macosX64()
    macosArm64()

    js(compiler = IR) {
        browser()
    }

    wasmJs {
        browser()
    }

    sourceSets {
        commonMain.dependencies {
            // compose
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
            // other
            implementation(libs.compose.continuousRoundedCornerShape)
        }
    }
}

android {
    namespace = "com.adamglin.zithian.ui"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    buildFeatures {
        compose = true
    }
}