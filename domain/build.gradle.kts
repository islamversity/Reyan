import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.islamversity.reyan.Deps

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("co.touchlab.native.cocoapods")

}

kotlin {
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

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "1.8"
}

kotlin {

    sourceSets["commonMain"].dependencies {
        implementation(kotlin(Deps.Kotlin.common))

        implementation(project(Deps.Modules.core))
        implementation(project(Deps.Modules.db))

        implementation(Deps.Coroutines.common)
        implementation(Deps.Serialization.core)

        implementation(Deps.ktor.Core.common)
        implementation(Deps.ktor.Json.common)
        implementation(Deps.ktor.Serialization.common)

        implementation(Deps.Tools.stately)

        implementation(Deps.Kotlin.dateTime)
        implementation(Deps.Tools.logger)

        implementation(Deps.Tools.uuid)
    }

    jvm()
    sourceSets["jvmMain"].dependencies {
        implementation(kotlin("stdlib", Versions.kotlin))
        implementation(Deps.Coroutines.jdk)

        implementation(Deps.ktor.Core.jvm)
        implementation(Deps.ktor.Json.jvm)
        implementation(Deps.ktor.Serialization.jvm)
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


    sourceSets.create("nativeTest")
    sourceSets.create("nativeMain")

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
            languageSettings.enableLanguageFeature("InlineClasses")
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