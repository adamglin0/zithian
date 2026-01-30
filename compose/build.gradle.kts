@file:OptIn(ExperimentalWasmDsl::class)

import com.android.build.api.dsl.androidLibrary
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.multiplatform.android.library)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.mavenPublish)
    alias(libs.plugins.binaryCompatibilityValidator)
    alias(libs.plugins.compose.hotReload)
}

group = "com.adamglin.zithian"

version = "1.0.0"

kotlin {
    jvmToolchain(17)

    @Suppress("UnstableApiUsage")
    androidLibrary {
        namespace = "com.adamglin.zithian.compose"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = 29

        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
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

compose {
    resources {
        publicResClass = true
        generateResClass = always
        nameOfResClass = "ZithianResources"
    }
}

mavenPublishing {
    coordinates(
        groupId = "com.adamglin.zithian",
        artifactId = "compose",
        version = "1.0.0"
    )
    pom {
        name.set("zithian")
        description.set("Zithian theme for Compose Multiplatform.")
        url.set("https://github.com/adamglin0/zithian")
        licenses {
            license {
                name.set("MIT")
                url.set("https://opensource.org/licenses/MIT")
            }
        }
        developers {
            developer {
                name.set("adamglin")
                email.set("dev@adamglin.com")
            }
        }
        issueManagement {
            system.set("Github")
            url.set("https://github.com/adamglin0/zithian/issues")
        }
        scm {
            connection.set("https://github.com/adamglin0/zithian.git")
            url.set("https://github.com/adamglin0/zithian")
        }
    }
}