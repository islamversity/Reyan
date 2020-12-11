package com.islamversity.reyan

import org.gradle.api.internal.artifacts.dependencies.DefaultExternalModuleDependency

object Versions {

    val kotlin = "1.4.10"
    val detekt = "1.10.0"
    val androidXTest = "1.1.0"
    val supportJunitExt = "1.0.0"
    val androidSupport = "1.0.0"
    val googleMaterial = "1.2.1"
    val constraintLayout = "2.0.0"
    val multidex = "2.0.0"
    val androidArchComponent = "2.2.0"
    val androidKotlinExt = "1.0.0"
    val okHttp = "4.0.1"
    val dagger = "2.27"
    val findBugs = "3.0.2"
    val jetBrainsAnnotation = "17.0.0"

    val timber = "4.7.1"
    val fresco = "2.0.0"
    val conductor = "3.0.0"
    val epoxy = "4.1.0"
    val flipper = "0.31.1"
    val chucker = "3.1.2"
    val soLoader = "0.5.1"
    val stetho = "1.5.1"
    val leakCanary = "2.4"

    val espresso = "3.1.0-alpha3"
    val robolectric = "4.2"
    val mockk = "1.9.3"
    val android_gradle_plugin = "3.6.2"
    val junit = "4.12"
    val truth = "1.0.1"


    val sqlDelight = "1.4.4"
    val ktor = "1.4.1"
    val serialization = "1.0.0"
    val stately = "1.1.0"
    val coroutines = "1.4.2-native-mt"//"1.3.9-native-mt"
    val flowBinding = "1.3.2"
    val koin = "3.0.0-alpha-9"
    val cocoapodsext = "0.6"

}

object Deps {

    object Kotlin {
        val common = "stdlib-common"
        val jvmStd8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
        val dateTime = "org.jetbrains.kotlinx:kotlinx-datetime:0.1.0"
        val reflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
    }

    object Android {

        val android_gradle_plugin =
            "com.android.tools.build:gradle:${Versions.android_gradle_plugin}"

        object Support {
            val coreKts = "androidx.core:core-ktx:${Versions.androidKotlinExt}"
            val compat = "androidx.appcompat:appcompat:${Versions.androidSupport}"
            val supportLegacy = "androidx.legacy:legacy-support-v4:${Versions.androidSupport}"
            val androidxCore = "androidx.core:core:${Versions.androidSupport}"
            val design = "com.google.android.material:material:${Versions.googleMaterial}"
            val cardView = "androidx.cardview:cardview:${Versions.androidSupport}"
            val annotation = "androidx.annotation:annotation:${Versions.androidSupport}"
            val vectorDrawable = "androidx.vectordrawable:vectordrawable:${Versions.androidSupport}"
            val recyclerView = "androidx.recyclerview:recyclerview:${Versions.androidSupport}"
            val multiDex = "androidx.multidex:multidex:${Versions.multidex}"
            val constraintLayout =
                "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
            val sqlite = "androidx.sqlite:sqlite-framework:2.1.0"
            val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.androidArchComponent}"
            val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.androidArchComponent}"
        }

        object Firebase {
            val core = "com.google.firebase:firebase-core:17.0.1"
            val analytics = "com.google.firebase:firebase-analytics:17.5.0"
            val crashlytics = "com.crashlytics.sdk.android:crashlytics:2.10.1"
            val messaging = "com.google.firebase:firebase-messaging:20.2.4"
            val remoteConfig = "com.google.firebase:firebase-config:18.0.0"
        }

        object Networking {
            val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
            val okHttpLogging = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
            val mockWebServer = "com.squareup.okhttp3:mockwebserver:4.0.1"
        }

        object Tools {
            val timber = "com.jakewharton.timber:timber:4.7.1"
            val fresco = "com.facebook.fresco:fresco:${Versions.fresco}"
            val frescoOkHttp = "com.facebook.fresco:imagepipeline-okhttp3:${Versions.fresco}"
            val conductor = "com.bluelinelabs:conductor:${Versions.conductor}"
            val conductorViewPager = "com.bluelinelabs:conductor-viewpager:${Versions.conductor}"
            val conductorAndroidxTransitions = "com.bluelinelabs:conductor-androidx-transition:${Versions.conductor}"
            val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanary}"
            val epoxy = "com.airbnb.android:epoxy:${Versions.epoxy}"
            val epoxyCompiler = "com.airbnb.android:epoxy-processor:${Versions.epoxy}"
            val epoxyCompilerDep =
                DefaultExternalModuleDependency(
                    "com.airbnb.android",
                    "epoxy-processor",
                    Versions.epoxy
                )
            val slider = "com.github.worldsnas:slider:1.0.3"
            val flipper = "com.facebook.flipper:flipper:${Versions.flipper}"
            val flipperNoOp = "com.facebook.flipper:flipper-noop:${Versions.flipper}"
            val flipperNetworkPlugin =
                "com.facebook.flipper:flipper-network-plugin:${Versions.flipper}"
            val flipperFrescoPlugin =
                "com.facebook.flipper:flipper-fresco-plugin:${Versions.flipper}"
            val chucker = "com.github.ChuckerTeam.Chucker:library:${Versions.chucker}"
            val chuckerNoop = "com.github.ChuckerTeam.Chucker:library-no-op:${Versions.chucker}"
            val soLoader = "com.facebook.soloader:soloader:${Versions.soLoader}"
            val stetho = "com.facebook.stetho:stetho:1.5.1"

            val coreDesugaringLib =
                DefaultExternalModuleDependency(
                    "com.android.tools",
                    "desugar_jdk_libs",
                    "1.0.5"
                )

            val seekBar = "com.github.warkiz.widget:indicatorseekbar:2.1.2"
            val localization = "com.akexorcist:localization:1.2.6"
        }

        object Test {
            val core = "androidx.test:core:${Versions.androidXTest}"
            val junit = "androidx.test.ext:junit:${Versions.androidXTest}"
            val runner = "androidx.test:runner:${Versions.androidXTest}"
            val rules = "androidx.test:rules:${Versions.androidXTest}"
            val junitExt = "androidx.test.ext:junit:1.0.0"
            val truth = "com.google.truth:truth:${Versions.truth}"
            val mockkUnit = "io.mockk:mockk:${Versions.mockk}"
            val mockkAndroid = "io.mockk:mockk-android:${Versions.mockk}"

            val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
            val espressoIntents =
                "androidx.test.espresso:espresso-intents:${Versions.espresso}"
            val espressoContrib =
                "androidx.test.espresso:espresso-contrib:${Versions.espresso}"


            val jsonTest = "org.json:json:20140107"

            val robolectric = "org.robolectric:robolectric:4.2"
        }
    }

    object Modules {
        val appModule = ":app"
        val kotlinTestHelper = ":kotlintesthelpers"
        val domain = ":domain"
        val daggerCore = ":daggercore"
        val base = ":base"
        val core = ":core"
        val navigation = ":navigation"
        val quranHome = ":quranHome"
        val settings = ":settings"
        val db = ":db"
        val viewComponent = ":view-component"
        val surah = ":surah"
        val search = ":search"
    }

    object Firebase {
        val analytics = "com.google.firebase:firebase-analytics-ktx:18.0.0"
        val crashlyticsSDK = "com.google.firebase:firebase-crashlytics-ktx:17.3.0"
        val messaging = "com.google.firebase:firebase-messaging-ktx:21.0.0"
        val remoteConfig = "com.google.firebase:firebase-config-ktx:20.0.1"
    }

    object Dagger {
        val dagger = "com.google.dagger:dagger:${Versions.dagger}"
        val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
        val daggerCompilerDep = DefaultExternalModuleDependency(
            "com.google.dagger",
            "dagger-compiler",
            Versions.dagger
        )
        val findBugs = "com.google.code.findbugs:jsr305:3.0.2"

        val jsrAnnotation = "javax.annotation:jsr250-api:1.0"
        val injectAnnotation = "javax.inject:javax.inject:1"
        val jetbrainsAnnotation = "org.jetbrains:annotations:17.0.0"
    }

    object KotlinTest {
        //        val common = "org.jetbrains.kotlin:kotlin-test-common:${Versions.kotlin}"
        val common = "org.jetbrains.kotlin:kotlin-test-multiplatform"
        val annotations = "org.jetbrains.kotlin:kotlin-test-annotations-common:${Versions.kotlin}"
        val jvm = "org.jetbrains.kotlin:kotlin-test:${Versions.kotlin}"
        val junit = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"
    }

    object Coroutines {
        val common = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        val jdk = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
        val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
        val flowBinding = "ru.ldralighieri.corbind:corbind:${Versions.flowBinding}"
        val turbine = "app.cash.turbine:turbine:0.2.1"
        val coroutinesWorker = "com.autodesk:coroutineworker:0.6.2"
    }

    object SqlDelight {
        val gradle = "com.squareup.sqldelight:gradle-plugin:${Versions.sqlDelight}"
        val runtime = "com.squareup.sqldelight:runtime:${Versions.sqlDelight}"
        val jvmTest = "com.squareup.sqldelight:sqlite-driver:${Versions.sqlDelight}"
        val driverNative = "com.squareup.sqldelight:native-driver:${Versions.sqlDelight}"
        val driverMacOS = "com.squareup.sqldelight:native-driver-macosx64:${Versions.sqlDelight}"
        val driverAndroid = "com.squareup.sqldelight:android-driver:${Versions.sqlDelight}"
        val coroutines = "com.squareup.sqldelight:coroutines-extensions:${Versions.sqlDelight}"
    }

    object Serialization {
        val core = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.serialization}"
    }

    object ktor {

        object Core {
            val common = "io.ktor:ktor-client-core:${Versions.ktor}"
            val jvm = "io.ktor:ktor-client-core-jvm:${Versions.ktor}"
        }

        object Engines {
            val okHttp = "io.ktor:ktor-client-okhttp:${Versions.ktor}"
            val ios = "io.ktor:ktor-client-ios:${Versions.ktor}"
            val native = "io.ktor:ktor-client-core-native:${Versions.ktor}"
        }

        object Json {
            val common = "io.ktor:ktor-client-json:${Versions.ktor}"
            val jvm = "io.ktor:ktor-client-json-jvm:${Versions.ktor}"
            val ios = "io.ktor:ktor-client-json-native:${Versions.ktor}"
            val js = "io.ktor:ktor-client-json-js:${Versions.ktor}"
        }

        object Serialization {
            val common = "io.ktor:ktor-client-serialization:${Versions.ktor}"
            val jvm = "io.ktor:ktor-client-serialization-jvm:${Versions.ktor}"
            val native = "io.ktor:ktor-client-serialization-native:${Versions.ktor}"
        }

        object Logger {
            val common = "io.ktor:ktor-client-logging:${Versions.ktor}"
            val jvm = "io.ktor:ktor-client-logging-jvm:${Versions.ktor}"
            val native = "io.ktor:ktor-client-logging-native:${Versions.ktor}"
            val js = "io.ktor:ktor-client-logging-js:${Versions.ktor}"
        }
    }

    object Tools {
        val stately = "co.touchlab:stately-common:${Versions.stately}"
        val statelyIso = "co.touchlab:stately-isolate:${Versions.stately}"
        val statelyConcurrency = "co.touchlab:stately-concurrency:${Versions.stately}"
        val koinCore = "org.koin:koin-core:${Versions.koin}"
        val cocoapodsext = "co.touchlab:kotlinnativecocoapods:${Versions.cocoapodsext}"
        val okio = "com.squareup.okio:okio:2.9.0"
        val uuid = "com.benasher44:uuid:0.2.2"
        val logger = "co.touchlab:kermit:0.1.8"
    }
}
