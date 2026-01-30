@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.mavenPublish)
    alias(libs.plugins.binaryCompatibilityValidator)
    alias(libs.plugins.ksp)
}

group = "com.adamglin.zithian"

version = "1.0.0"

kotlin {
    jvmToolchain(17)

    jvm {}

    sourceSets {
        commonMain.dependencies {
            // compose
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            // other
            implementation(projects.zithian.compose)
            implementation(projects.zithian.processor.api)
            implementation(libs.haze)
            implementation(libs.liquid)
            api(libs.composeContinuousRoundedCornerShape)
            implementation(libs.coil.compose)
            implementation(libs.coil.svg)
            api(libs.kotlinx.collections.immutable)
            api(libs.jbr.api)
        }
        jvmMain.configure {
            kotlin.srcDir("build/generated/ksp/jvm/jvmMain/kotlin")
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

dependencies {
    add("kspJvm", projects.processor.ksp)
    add("kspJvmTest", projects.processor.ksp)
}

compose {
    resources {
        publicResClass = true
        generateResClass = always
        nameOfResClass = "EmulatorResources"
    }
}
mavenPublishing {
    coordinates(
        groupId = "com.adamglin.zithian",
        artifactId = "emulator",
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