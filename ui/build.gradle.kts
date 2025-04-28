@file:OptIn(ExperimentalWasmDsl::class)

import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
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
    alias(libs.plugins.detekt)
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

detekt {
    buildUponDefaultConfig = true
    allRules = false
    config.setFrom("$rootDir/.detekt/config.yml") // point to your custom config defining rules to run, overwriting default behavior
    baseline = file("$rootDir/.detekt/baseline.xml") // a way of suppressing issues before introducing detekt
}
tasks.withType<Detekt>().configureEach {
    reports {
        md.required.set(true)
    }
}
tasks.withType<Detekt>().configureEach {
    jvmTarget = JvmTarget.JVM_1_8.target
}
tasks.withType<DetektCreateBaselineTask>().configureEach {
    jvmTarget = JvmTarget.JVM_1_8.target
}

dependencies {
    detektPlugins(libs.detekt.formatting)
    detektPlugins(libs.detekt.rules.compose)
}