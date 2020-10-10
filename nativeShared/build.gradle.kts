plugins {
    kotlin("multiplatform")
}

kotlin {
    sourceSets["commonMain"].dependencies {
        implementation(kotlin(Deps.Kotlin.common))
        implementation(Deps.Coroutines.common)
        implementation(Deps.Tools.logger)

//        implementation(project(Deps.Modules.core))
//        implementation(project(Deps.Modules.db))
//        implementation(project(Deps.Modules.domain))
//        implementation(project(Deps.Modules.navigation))
//
//        features
//        implementation(project(Deps.Modules.quranHome))
//        implementation(project(Deps.Modules.settings))
//        implementation(project(Deps.Modules.surah))
//        implementation(project(Deps.Modules.search))
    }
    sourceSets["commonTest"].dependencies {
        implementation(kotlin(Deps.Kotlin.common))
        implementation(Deps.KotlinTest.common)
        implementation(Deps.Coroutines.turbine)
    }

    iosArm32()
    iosArm64()
    iosX64()
    linuxX64()
    macosX64()
    mingwX64()
    tvosArm64()
    tvosX64()
    watchosArm32()
    watchosArm64()
    watchosX86()

    sourceSets.create("nativeMain").dependencies {
    }
    sourceSets.create("nativeTest").dependencies {
    }

    configure(listOf(
        targets["iosArm32"],
        targets["iosArm64"],
        targets["iosX64"],
        targets["linuxX64"],
        targets["macosX64"],
        targets["mingwX64"],
        targets["tvosArm64"],
        targets["tvosX64"],
        targets["watchosArm32"],
        targets["watchosArm64"],
        targets["watchosX86"]
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
}