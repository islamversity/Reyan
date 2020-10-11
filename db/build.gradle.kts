plugins {
    kotlin("multiplatform")
    id("com.squareup.sqldelight")
    id("co.touchlab.native.cocoapods")
}

sqldelight {
    database("Main") {
        packageName = "com.islamversity.db"
        sourceFolders = listOf("sqldelight")

        // The directory where to store '.db' schema files relative to the root of the project.
        // These files are used to verify that migrations yield a database with the latest schema.
        // Defaults to null so the verification tasks will not be created.
        schemaOutputDirectory = file("src/main/sqldelight/db")
    }
}

kotlin {
    sourceSets["commonMain"].dependencies {
        implementation(kotlin("stdlib", Versions.kotlin))
        implementation(Deps.SqlDelight.runtime)
        implementation(Deps.Coroutines.common)
    }

    jvm()

    js {
        nodejs()
    }

    ios()
    watchos()
    tvos()

    linuxX64()
    macosX64("macos")
    mingwX64()

    sourceSets["iosMain"].dependencies {
        implementation(Deps.SqlDelight.driverNative)
    }

    sourceSets["watchosMain"].dependencies {
        implementation(Deps.SqlDelight.driverNative)
    }
    sourceSets["macosMain"].dependencies {
        implementation(Deps.SqlDelight.driverMacOS)
    }
    sourceSets.create("nativeTest")
    sourceSets.create("nativeMain")

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
