plugins {
    id("com.android.library")
    kotlin("android")
    id("kotlin-android-extensions")
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
    packagingOptions {
        exclude("META-INF/NOTICE")
        exclude("META-INF/NOTICE.txt")
        exclude("META-INF/LICENSE")
        exclude("META-INF/LICENSE.txt")
        exclude("META-INF/services/javax.annotation.processing.Processor")
        exclude("META-INF/maven/com.google.guava/guava/pom.properties")
        exclude("META-INF/maven/com.google.guava/guava/pom.xml")
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/*.kotlin_module")
        exclude("META-INF/AL2.0")
        exclude("META-INF/LGPL2.1")
        pickFirst("META-INF/kotlinx-coroutines-core.kotlin_module")
    }
}

dependencies {
    implementation(project(Deps.Modules.db))
    implementation(project(Deps.Modules.daggerCore))
    implementation(Deps.Kotlin.jvmStd8)
    androidTestImplementation(Deps.Android.Test.junit)
    androidTestImplementation(Deps.Android.Test.truth)
    androidTestImplementation(Deps.Android.Test.runner)
    androidTestImplementation(Deps.Android.Test.core)
    androidTestImplementation(Deps.Android.Test.rules)
    androidTestImplementation(Deps.Android.Test.junitExt)
    androidTestImplementation(Deps.Android.Test.espressoCore)
    androidTestImplementation(Deps.Android.Test.espressoIntents)
    androidTestImplementation(Deps.ktor.Serialization.jvm)
    androidTestImplementation(Deps.SqlDelight.driverAndroid)
    androidTestImplementation(Deps.Coroutines.test)
    androidTestImplementation(Deps.Coroutines.turbine)

}