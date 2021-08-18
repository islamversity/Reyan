package com.islamversity.db.datasource

import com.islamversity.db.*
import com.islamversity.db.model.EntityUUID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import com.islamversity.db.model.Calligraphy
import com.islamversity.db.model.NameId
import com.islamversity.db.model.RawId
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

interface NameLocalDataSource {

    suspend fun insertName(name: No_rowId_name, context: CoroutineContext = Dispatchers.Default)

    fun getNameByContent(content: String, context: CoroutineContext = Dispatchers.Default): Flow<Name?>

    fun getNameById(id: NameId, context: CoroutineContext = Dispatchers.Default): Flow<Name?>

    fun getNameByParentIdAndCalligraphy(
            parentId: RawId,
            calligraphy: Calligraphy,
            context: CoroutineContext = Dispatchers.Default
    ): Flow<Name?>

    fun observeAllNamesForParent(parentId: RawId, context: CoroutineContext = Dispatchers.Default): Flow<List<Name>>

    fun observeAllNames(context: CoroutineContext = Dispatchers.Default): Flow<List<Name>>
}

class NameLocalDataSourceImpl(
        private val queries: NameQueries
) : NameLocalDataSource {
    override suspend fun insertName(name: No_rowId_name, context: CoroutineContext) {
        withContext(context) {
            queries.insertName(name.id, name.rowId, name.calligraphy, name.content)
        }
    }

    override fun getNameByContent(content: String, context: CoroutineContext): Flow<Name?> =
            queries.getNameId(content)
                    .asFlow()
                    .mapToOneOrNull(context)

    override fun getNameById(id: NameId, context: CoroutineContext): Flow<Name?> =
            queries.getNameContent(id)
                    .asFlow()
                    .mapToOneOrNull(context)

    override fun getNameByParentIdAndCalligraphy(
            parentId: RawId,
            calligraphy: Calligraphy,
            context: CoroutineContext
    ): Flow<Name?> =
            queries.getNameByParentIdAndCalligraphy(parentId, calligraphy)
                    .asFlow()
                    .mapToOneOrNull(context)

    override fun observeAllNamesForParent(
            parentId: RawId,
            context: CoroutineContext
    ): Flow<List<Name>> =
            queries.getAllNamesForParentId(parentId)
                    .asFlow()
                    .mapToList(context)

    override fun observeAllNames(context: CoroutineContext): Flow<List<Name>> =
            queries.getAllNames()
                    .asFlow()
                    .mapToList(context)
}