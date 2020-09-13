plugins {
    kotlin("multiplatform")
}

kotlin {
    sourceSets["commonMain"].dependencies {
        implementation(kotlin(Deps.Kotlin.common))
        implementation(Deps.SqlDelight.runtime)
        implementation(Deps.Coroutines.common)
        implementation(Deps.Tools.logger)
    }

    jvm()
    sourceSets["jvmTest"].dependencies {
        implementation(Deps.Coroutines.test)
        implementation(Deps.Android.Test.junit)
        implementation(Deps.Android.Test.truth)
    }
    sourceSets["jvmMain"].dependencies {
        implementation(kotlin("stdlib", Versions.kotlin))
        implementation(Deps.Coroutines.jdk)
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