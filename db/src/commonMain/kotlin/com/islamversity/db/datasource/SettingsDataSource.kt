package com.islamversity.db.datasource

import com.islamversity.db.SettingsQueries
import com.islamversity.db.asFlow
import com.islamversity.db.mapToOneOrNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

interface SettingsDataSource {

    suspend fun put(key: String, value: String?, context: CoroutineContext = Dispatchers.Default)
    fun get(key: String, context: CoroutineContext = Dispatchers.Default): String?

    fun observeKey(key: String, context: CoroutineContext = Dispatchers.Default): Flow<String?>

    fun observeKey(
            key: String,
            defaultValue: String,
            context: CoroutineContext = Dispatchers.Default
    ): Flow<String> = observeKey(key, { defaultValue }, context)

    fun observeKey(
            key: String,
            defaultValue: suspend () -> String,
            context: CoroutineContext = Dispatchers.Default
    ): Flow<String>
}

class SettingsDataSourceImpl(
        private val queries: SettingsQueries
) : SettingsDataSource {
    override suspend fun put(key: String, value: String?, context: CoroutineContext) {
        withContext(context) {
            queries.upsert(key, value)
        }
    }

    override fun get(key: String, context: CoroutineContext): String? =
            queries.getWithKey(key).executeAsOneOrNull()?.value

    override fun observeKey(key: String, context: CoroutineContext): Flow<String?> =
            queries.getWithKey(key)
                    .asFlow()
                    .mapToOneOrNull(context)
                    .map {
                        it?.value
                    }

    override fun observeKey(
            key: String,
            defaultValue: suspend () -> String,
            context: CoroutineContext
    ): Flow<String> =
            observeKey(key, context)
                    .distinctUntilChanged()
                    .map {
                        it ?: defaultValue()
                    }
}
