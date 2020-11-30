import com.islamversity.reyan.Deps

plugins {
    kotlin("multiplatform")
//    id("com.android.dynamic-feature")
    id("com.android.library")
    kotlin("kapt")
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

    android.buildFeatures.viewBinding = true

    compileOptions {
        // Flag to enable support for the new language APIs
//        isCoreLibraryDesugaringEnabled = true
        // Sets Java compatibility to Java 8
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

kotlin {
    sourceSets {
        all {
            languageSettings.apply {
                useExperimentalAnnotation("kotlin.ExperimentalStdlibApi")
                useExperimentalAnnotation("kotlin.time.ExperimentalTime")
            }
        }
    }
    sourceSets["commonMain"].dependencies {
        implementation(kotlin(Deps.Kotlin.common))
        implementation(project(Deps.Modules.domain))
        implementation(project(Deps.Modules.core))
        implementation(project(Deps.Modules.navigation))
        implementation(Deps.Coroutines.common)
    }
    android()
    sourceSets["androidMain"].dependencies {
        implementation(kotlin("stdlib", Versions.kotlin))

        implementation(Deps.Android.Support.compat)
        implementation(Deps.Android.Support.constraintLayout)
        implementation(Deps.Android.Support.design)

        implementation(Deps.Android.Support.coreKts)

        implementation(Deps.Android.Tools.conductor)

        implementation(Deps.Dagger.dagger)
        implementation(Deps.Dagger.jsrAnnotation)

        configurations.get("kapt").dependencies.add(
            Deps.Dagger.daggerCompilerDep
        )

        implementation(Deps.Android.Tools.epoxy)
        configurations.get("kapt").dependencies.add(
            Deps.Android.Tools.epoxyCompilerDep
        )

        implementation(Deps.Android.Tools.seekBar)
        implementation(Deps.Android.Tools.localization)

        implementation(Deps.Coroutines.jdk)
        implementation(Deps.Coroutines.flowBinding)

        implementation(project(Deps.Modules.daggerCore))
        implementation(project(Deps.Modules.base))
        implementation(project(Deps.Modules.navigation))
        implementation(project(Deps.Modules.viewComponent))
    }

    sourceSets["androidTest"].dependencies {
        implementation(kotlin("stdlib", Versions.kotlin))
        implementation(Deps.Android.Test.junit)
        implementation(Deps.Android.Test.truth)
        implementation(Deps.Android.Test.runner)
        implementation(Deps.Android.Test.core)
        implementation(Deps.Android.Test.rules)
        implementation(Deps.Android.Test.junitExt)
        implementation(Deps.Android.Test.espressoCore)
        implementation(Deps.Android.Test.espressoIntents)
    }
    jvm()
    sourceSets["jvmTest"].dependencies {
        implementation(kotlin("stdlib", Versions.kotlin))
        implementation(Deps.Android.Test.junit)
        implementation(Deps.Android.Test.truth)
        implementation(Deps.Android.Test.mockkUnit)
        implementation(Deps.Android.Test.robolectric)
        implementation(Deps.Android.Test.runner)
        implementation(Deps.Android.Test.core)
        implementation(Deps.Android.Test.rules)
        implementation(Deps.Android.Test.junitExt)
        implementation(Deps.Android.Test.espressoCore)
        implementation(Deps.Android.Test.jsonTest)
        implementation(project(Deps.Modules.kotlinTestHelper))
    }

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
