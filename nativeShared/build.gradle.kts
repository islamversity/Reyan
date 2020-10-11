plugins {
    kotlin("multiplatform")
    id("co.touchlab.native.cocoapods")
}

kotlin {
    sourceSets["commonMain"].dependencies {
        implementation(kotlin(Deps.Kotlin.common))
        implementation(Deps.Coroutines.common)
        implementation(Deps.Tools.logger)

        api(project(Deps.Modules.core))
        api(project(Deps.Modules.db))
        api(project(Deps.Modules.domain))
        api(project(Deps.Modules.navigation))

        //features
        api(project(Deps.Modules.quranHome))
        api(project(Deps.Modules.settings))
        api(project(Deps.Modules.surah))
        api(project(Deps.Modules.search))
    }
    sourceSets["commonTest"].dependencies {
        implementation(kotlin(Deps.Kotlin.common))
        implementation(Deps.KotlinTest.common)
        implementation(Deps.Coroutines.turbine)
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
            export(project(Deps.Modules.core))
            export(project(Deps.Modules.db))
            export(project(Deps.Modules.domain))
            export(project(Deps.Modules.navigation))

            export(project(Deps.Modules.quranHome))
            export(project(Deps.Modules.settings))
            export(project(Deps.Modules.surah))
            export(project(Deps.Modules.search))
            transitiveExport = true
            homepage = "$name home"
            setVersion("1.0")
        }
    }
}