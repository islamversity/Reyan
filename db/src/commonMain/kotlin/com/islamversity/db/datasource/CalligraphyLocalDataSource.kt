package com.islamversity.db.datasource

import com.islamversity.db.*
import com.islamversity.db.model.*
import com.islamversity.db.Calligraphy as CalligraphyEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

interface CalligraphyLocalDataSource {
    suspend fun insertCalligraphy(
        id: CalligraphyId,
        lang: LanguageCode,
        name: CalligraphyName,
        friendlyName: String,
        code: Calligraphy,
        context: CoroutineContext = Dispatchers.Default
    )

    fun getCalligraphyByCode(
        code: Calligraphy,
        context: CoroutineContext = Dispatchers.Default
    ): Flow<CalligraphyEntity?>

    fun getCalligraphyById(
        id: CalligraphyId,
        context: CoroutineContext = Dispatchers.Default
    ): Flow<CalligraphyEntity?>

    fun observeAllCallygraphies(context: CoroutineContext = Dispatchers.Default): Flow<List<CalligraphyEntity>>

    fun getAllSurahNameCalligraphies(context: CoroutineContext = Dispatchers.Default): Flow<List<CalligraphyEntity>>
    fun getAllAyaCalligraphies(context: CoroutineContext = Dispatchers.Default): Flow<List<CalligraphyEntity>>

    fun getArabicSurahCalligraphy(context: CoroutineContext = Dispatchers.Default): Flow<CalligraphyEntity>
    fun getArabicSimpleAyaCalligraphy(context: CoroutineContext = Dispatchers.Default): Flow<CalligraphyEntity>
}

class CalligraphyLocalDataSourceImpl(
    private val queries: CalligraphyQueries
) : CalligraphyLocalDataSource {

    override suspend fun insertCalligraphy(
        id: CalligraphyId,
        lang: LanguageCode,
        name: CalligraphyName,
        friendlyName: String,
        code: Calligraphy,
        context: CoroutineContext
    ) {
        withContext(context) {
            queries.insertCalligraphy(id, lang, name, friendlyName, code)
        }
    }

    override fun getCalligraphyByCode(
        code: Calligraphy,
        context: CoroutineContext
    ): Flow<CalligraphyEntity?> =
        queries.getCalligraphyId(code)
            .asFlow()
            .mapToOneOrNull(context)

    override fun getCalligraphyById(
        id: CalligraphyId,
        context: CoroutineContext
    ): Flow<CalligraphyEntity?> =
        queries.getCalligraphyCode(id)
            .asFlow()
            .mapToOneOrNull(context)
            .onEach {
//                Logger.log {
//                    "GetSurah :  getSecondarySurahNameCalligraphy : calligraphy = "  + it.toString()
//                }
            }

    override fun observeAllCallygraphies(context: CoroutineContext): Flow<List<CalligraphyEntity>> =
        queries.getAllCalligraphies()
            .asFlow()
            .mapToList(context)

    override fun getAllSurahNameCalligraphies(context: CoroutineContext): Flow<List<com.islamversity.db.Calligraphy>> =
        queries.getSurahNameCalligraphies { calligraphy, rowIndex, languageCode, name, friendlyName, code ->
            CalligraphyEntity(rowIndex!!, calligraphy, languageCode!!, name, friendlyName!!, code!!)
        }
            .asFlow()
            .mapToList(context)

    override fun getAllAyaCalligraphies(context: CoroutineContext): Flow<List<com.islamversity.db.Calligraphy>> =
        queries.getAyaCalligraphies { calligraphy, rowIndex, languageCode, name, friendlyName, code ->
            CalligraphyEntity(rowIndex!!, calligraphy, languageCode!!, name, friendlyName!!, code!!)
        }
            .asFlow()
            .mapToList(context)

    override fun getArabicSurahCalligraphy(context: CoroutineContext): Flow<CalligraphyEntity> =
        queries.getArabicSurahCalligraphy()
            .asFlow()
            .mapToOne(context)

    override fun getArabicSimpleAyaCalligraphy(context: CoroutineContext): Flow<CalligraphyEntity> =
        queries.getSimpleArabicSurahContentCalligraphy()
            .asFlow()
            .mapToOne(context)
}