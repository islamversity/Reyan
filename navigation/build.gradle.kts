import com.islamversity.reyan.Deps

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("com.android.library")
}

android {
    compileSdkVersion(prjectCompileSdkVersion)
    defaultConfig {
        minSdkVersion(projectMinSdkVersion)
        targetSdkVersion(projectTargetSdkVersion)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

kotlin {
    sourceSets {
        all {
            languageSettings.apply {
                useExperimentalAnnotation("kotlin.ExperimentalStdlibApi")
            }
        }
    }
    sourceSets["commonMain"].dependencies {
        implementation(kotlin("stdlib", Versions.kotlin))
        implementation(Deps.Coroutines.common)
        implementation(Deps.Serialization.core)
    }
    android()
    sourceSets["androidMain"].dependencies {
        implementation(kotlin("stdlib", Versions.kotlin))
        implementation(Deps.Android.Tools.conductor)
        implementation(Deps.Android.Tools.conductorAndroidxTransitions)
        implementation(Deps.Android.Support.compat)
        implementation(Deps.Coroutines.android)
    }
    sourceSets["androidTest"].dependencies {
        implementation(kotlin("stdlib", Versions.kotlin))
        implementation(Deps.Android.Test.junit)
        implementation(Deps.Android.Test.truth)
    }

    jvm()

    ios()
    sourceSets["iosMain"].dependencies {
    }

    watchos()
    tvos()

    linuxX64()
    macosX64("macos")
    mingwX64()

    sourceSets.create("nativeMain")
    sourceSets.create("nativeTest")

    configure(
        listOf(
            targets["iosArm64"],
            targets["iosX64"],

            targets["watchosArm32"],
            targets["watchosArm64"],
            targets["watchosX86"],

            targets["tvosArm64"],
            targets["tvosX64"],

            targets["linuxX64"],
            targets["macos"],
            targets["mingwX64"]
        )
    ) {
        compilations["main"].source(sourceSets["nativeMain"])
        compilations["test"].source(sourceSets["nativeTest"])
    }
}