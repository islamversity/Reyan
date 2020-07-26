import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
//    id("com.codingfeline.buildkonfig")
}

kotlin {
    sourceSets {
        all {
            languageSettings.apply {
                useExperimentalAnnotation("kotlinx.coroutines.ExperimentalCoroutinesApi")
                useExperimentalAnnotation("kotlinx.serialization.ImplicitReflectionSerializer")
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

        implementation(Deps.ktor.Core.common)
        implementation(Deps.ktor.Json.common)
        implementation(Deps.ktor.Serialization.common)

        implementation(Deps.Tools.stately)
        implementation(Deps.Tools.islandTime)

        implementation(Deps.Tools.islandTime)
    }

    jvm()
    sourceSets["jvmMain"].dependencies {
        implementation(kotlin("stdlib", Versions.kotlin))
        implementation(Deps.Coroutines.jdk)

        implementation(Deps.ktor.Core.jvm)
        implementation(Deps.ktor.Json.jvm)
        implementation(Deps.ktor.Serialization.jvm)
    }
}