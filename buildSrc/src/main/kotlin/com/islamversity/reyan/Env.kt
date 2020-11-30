import com.islamversity.reyan.Versions as MainVersions

val isReleaseDebuggable = false
val isDebugDebuggable = true

val isDebugMinify = false
val isReleaseMinify = true

val isReleaseMultiDex = false
val isDebugMultiDex = true

val isDebugShrinkResources = false
val isReleaseShrinkResources = false

val isDebugTestCoverage = true

val projectMinSdkVersion = 21
val projectTargetSdkVersion = 29
val prjectCompileSdkVersion = 29

val proguardFileAddress = "../proguard-rules.pro"

object Versions {
    val kotlin = MainVersions.kotlin
    val detekt = MainVersions.detekt
    val androidXTest = MainVersions.androidXTest
    val supportJunitExt = MainVersions.supportJunitExt
    val androidSupport = MainVersions.androidSupport
    val googleMaterial = MainVersions.googleMaterial
    val constraintLayout = MainVersions.constraintLayout
    val multidex = MainVersions.multidex
    val androidArchComponent = MainVersions.androidArchComponent
    val androidKotlinExt = MainVersions.androidKotlinExt
    val okHttp = MainVersions.okHttp
    val dagger = MainVersions.dagger
    val findBugs = MainVersions.findBugs
    val jetBrainsAnnotation = MainVersions.jetBrainsAnnotation

    val timber = MainVersions.timber
    val fresco = MainVersions.fresco
    val conductor = MainVersions.conductor
    val epoxy = MainVersions.epoxy
    val flipper = MainVersions.flipper
    val chucker = MainVersions.chucker
    val soLoader = MainVersions.soLoader
    val stetho = MainVersions.stetho
    val leakCanary = MainVersions.leakCanary

    val espresso = MainVersions.espresso
    val robolectric = MainVersions.robolectric
    val mockk = MainVersions.mockk
    val android_gradle_plugin = MainVersions.android_gradle_plugin
    val junit = MainVersions.junit
    val truth = MainVersions.truth


    val sqlDelight = MainVersions.sqlDelight
    val ktor = MainVersions.ktor
    val serialization = MainVersions.serialization
    val stately = MainVersions.stately
    val coroutines = MainVersions.coroutines
    val flowBinding = MainVersions.flowBinding
    val koin = MainVersions.koin
    val cocoapodsext = MainVersions.cocoapodsext
}