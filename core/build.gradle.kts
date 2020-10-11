plugins {
    kotlin("multiplatform")
    id("co.touchlab.native.cocoapods")
}

kotlin {
    sourceSets["commonMain"].dependencies {
        implementation(kotlin(Deps.Kotlin.common))
        implementation(Deps.Coroutines.common)
        implementation(Deps.Tools.logger)
        implementation(Deps.Tools.statelyConcurrency)
    }
    sourceSets["commonTest"].dependencies {
        implementation(kotlin(Deps.Kotlin.common))
        implementation(Deps.KotlinTest.common)
        implementation(Deps.Coroutines.turbine)
    }

    jvm()
    sourceSets["jvmTest"].dependencies {
        implementation(Deps.Coroutines.test)
        implementation(Deps.Android.Test.junit)
        implementation(Deps.Android.Test.truth)
    }

    js {
        nodejs()
    }


    ios()
    watchos()
    tvos()

    linuxX64()
    macosX64("macos")
    mingwX64()

    sourceSets.create("nativeMain")
    sourceSets.create("nativeTest")

    configure(listOf(
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
        )) {
        compilations["main"].source(sourceSets["nativeMain"])
        compilations["test"].source(sourceSets["nativeTest"])
    }

    sourceSets {
        all {
            languageSettings.apply {
                useExperimentalAnnotation("kotlinx.coroutines.ExperimentalCoroutinesApi")
                useExperimentalAnnotation("kotlinx.serialization.InternalSerializationApi")
                useExperimentalAnnotation("kotlin.time.ExperimentalTime")
            }
        }
    }

    cocoapodsext {
        summary = "shared $name module"
        framework {
            transitiveExport = true
            homepage = "$name home"
            setVersion("1.0")
        }
    }
}