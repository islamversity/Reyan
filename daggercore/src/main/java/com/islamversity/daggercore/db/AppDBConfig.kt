package com.islamversity.daggercore.db

import android.app.Application
import com.islamversity.db.Calligraphy
import com.islamversity.db.model.CalligraphyId
import com.islamversity.db.model.CalligraphyName
import com.islamversity.db.model.LanguageCode
import com.islamversity.domain.usecase.DatabaseFileConfig
import java.util.*

internal class AppDBConfig(
    private val app : Application
) : DatabaseFileConfig {
    override val assetVersion: Int = 1

    override fun arabicTextCalligraphy(): Calligraphy =
        Calligraphy(
            0,
            CalligraphyId(generateRandomUUID()),
            LanguageCode("ar"),
            CalligraphyName("simple"),
            "عربي-بسيط",
            com.islamversity.db.model.Calligraphy(LanguageCode("ar"), CalligraphyName("simple"))
        )

    override fun englishTextCalligraphy(): Calligraphy =
        Calligraphy(
            0,
            CalligraphyId(generateRandomUUID()),
            LanguageCode("en"),
            CalligraphyName("Abdullah Yusuf Ali"),
            "English-Yusuf Ali",
            com.islamversity.db.model.Calligraphy(LanguageCode("en"), CalligraphyName("Abdullah Yusuf Ali"))
        )
    override fun farsiTextCalligraphy(): Calligraphy =
        Calligraphy(
            0,
            CalligraphyId(generateRandomUUID()),
            LanguageCode("fa"),
            CalligraphyName("Makarem Shirazi"),
            "فارسی-مکارم شیرازی",
            com.islamversity.db.model.Calligraphy(LanguageCode("fa"), CalligraphyName("Makarem Shirazi"))
        )

    override fun generateRandomUUID() : String =
        UUID.randomUUID().toString()

    override fun getQuranArabicText(): ByteArray {
        return app
            .resources
            .assets
            .open("quran-simple-one-line.json")
            .use {
                val bytes = ByteArray(it.available())
                it.read(bytes)
                bytes
            }
    }

    override fun getQuranEnglishText(): ByteArray {
        return app
            .resources
            .assets
            .open("quran-en-Abdullah-Yusuf-Ali.json")
            .use {
                val bytes = ByteArray(it.available())
                it.read(bytes)
                bytes
            }
    }

    override fun getQuranFarsiText(): ByteArray {
        return app
            .resources
            .assets
            .open("quran-farsi-makarem.json")
            .use {
                val bytes = ByteArray(it.available())
                it.read(bytes)
                bytes
            }
    }

    override fun getQuranExtraData(): ByteArray {
        return app
            .resources
            .assets
            .open("quran-data.json")
            .use {
                val bytes = ByteArray(it.available())
                it.read(bytes)
                bytes
            }
    }
}
