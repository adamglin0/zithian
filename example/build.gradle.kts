import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose.hotReload)
}

kotlin {
    jvm {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_21
        }
    }

    sourceSets {
        commonMain.dependencies {
            // compose
            implementation(compose.desktop.currentOs) {
                exclude(group = "org.jetbrains.compose.material")
                exclude(group = "org.jetbrains.compose.material3")
            }
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.navigation3.runtime)
            implementation(libs.navigation3.ui)
            implementation(libs.navigation3.viewModel)
            // other
            implementation(projects.zithian.compose)
            implementation(projects.zithian.navigation3)
            implementation(projects.zithian.emulator)
            implementation(libs.coil.compose)
            implementation(libs.liquid)
        }
    }
    compilerOptions {
        freeCompilerArgs.add("-Xcontext-parameters")
    }
}
