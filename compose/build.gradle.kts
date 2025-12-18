@file:OptIn(ExperimentalWasmDsl::class)

import com.android.build.api.dsl.androidLibrary
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.dokka)
    alias(libs.plugins.mavenPublish)
    alias(libs.plugins.binaryCompatibilityValidator)
    alias(libs.plugins.detekt)
    alias(libs.plugins.compose.hotReload)
}

group = "com.adamglin.zithian"

version = "1.0.0"

kotlin {
    jvmToolchain(21)

    @Suppress("UnstableApiUsage")
    androidLibrary {
        namespace = "com.adamglin.zithian.compose"
        compileSdk = libs.versions.androidCompileSdk.get().toInt()
        minSdk = 29

        compilerOptions {
            jvmTarget = JvmTarget.JVM_21
        }
    }

    jvm {}

    iosArm64()
    iosSimulatorArm64()

    js(compiler = IR) {
        browser()
    }

    wasmJs {
        browser()
    }

    sourceSets {
        val skikoMain = create("skikoMain")
        val webMain = create("webMain")
        val androidAndJvmMain = create("androidAndJvmMain")
        skikoMain.dependsOn(commonMain.get())
        androidAndJvmMain.dependsOn(commonMain.get())
        webMain.dependsOn(skikoMain)
        jsMain.configure { dependsOn(webMain) }
        wasmJsMain.configure { dependsOn(webMain) }
        jvmMain.configure {
            dependsOn(androidAndJvmMain)
            dependsOn(skikoMain)
        }
        nativeMain.configure {
            dependsOn(skikoMain)
        }
        androidMain.configure {
            dependsOn(androidAndJvmMain)
        }
        appleMain.configure {
            dependsOn(nativeMain.get())
        }
        iosMain.configure {
            dependsOn(appleMain.get())
        }
        iosArm64Main.configure { dependsOn(iosMain.get()) }
        iosSimulatorArm64Main.configure { dependsOn(iosMain.get()) }

        commonMain.dependencies {
            // compose
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.navigation3.runtime)
            implementation(libs.navigation3.ui)
            // other
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

compose {
    resources {
        publicResClass = true
        generateResClass = always
        nameOfResClass = "ZithianResources"
    }
}