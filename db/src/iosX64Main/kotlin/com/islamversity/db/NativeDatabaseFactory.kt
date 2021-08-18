package com.islamversity.db

import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual object NativeDatabaseFactory {
    actual fun create(): Main {
        return createMainDB(NativeSqliteDriver(Main.Schema, "main.db"))
    }
}