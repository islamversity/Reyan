import com.islamversity.reyan.Versions as MainVersions

val isDebugDebuggable = true

val isDebugMinify = false
val isReleaseMinify = true

val isReleaseMultiDex = true
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
    val sqlDelight = MainVersions.sqlDelight
    val detekt = MainVersions.detekt
}