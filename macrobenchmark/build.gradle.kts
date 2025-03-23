plugins {
    id("com.android.test")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.naveenapps.expensemanager.benchmark"

    compileSdk = 34

    defaultConfig {
        minSdk = 23
        targetSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
    }

    buildTypes {
        // This benchmark buildType is used for benchmarking, and should function like your
        // release build (for example, with minification on). It's signed with a debug key
        // for easy local/CI testing.
        val macrobenchmark by creating {
            // Keep the build type debuggable so we can attach a debugger if needed.
            isDebuggable = true
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks.add("release")
        }
    }

    testOptions {
        managedDevices {
            devices {
                create("pixel2Api31", com.android.build.api.dsl.ManagedVirtualDevice::class) {
                    device = "Pixel 2"
                    apiLevel = 31
                    systemImageSource = "aosp"
                }
            }
        }
    }

    targetProjectPath = ":app"

    experimentalProperties["android.experimental.self-instrumenting"] = true

    defaultConfig {
        testInstrumentationRunnerArguments["androidx.benchmark.suppressErrors"] = "DEBUGGABLE"
        testInstrumentationRunnerArguments["androidx.benchmark.suppressErrors"] = "EMULATOR"
    }
}

dependencies {
    implementation(libs.androidx.test.ext)
    implementation(libs.androidx.test.espresso.core)
    implementation(libs.androidx.benchmark.macro)
    implementation(libs.androidx.profileinstaller)
}

androidComponents {
    beforeVariants {
        it.enable = it.buildType == "macrobenchmark"
    }
}