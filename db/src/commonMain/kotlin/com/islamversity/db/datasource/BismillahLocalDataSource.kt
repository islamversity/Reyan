package com.islamversity.db.datasource

import com.islamversity.db.BismillahQueries
import com.islamversity.db.asFlow
import com.islamversity.db.mapToOneOrNull
import com.islamversity.db.model.Bismillah
import com.islamversity.db.model.BismillahTypeFlag
import com.islamversity.db.model.CalligraphyId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

interface BismillahLocalDataSource {
    fun getBismillahWithFlag(
        flag: BismillahTypeFlag,
        calligraphyId: CalligraphyId,
        context: CoroutineContext = Dispatchers.Default
    ): Flow<Bismillah?>
}

class BismillahLocalDataSourceImpl(
    private val query : BismillahQueries
) : BismillahLocalDataSource {

    override fun getBismillahWithFlag(
        flag: BismillahTypeFlag,
        calligraphyId: CalligraphyId,
        context: CoroutineContext
    ): Flow<Bismillah?> =
        query.getBismillahByFlag(calligraphyId, flag) {rowIndex, id, bismillahFlag, content ->
            Bismillah(id, content!!, bismillahFlag)
        }
            .asFlow()
            .mapToOneOrNull(context)
}
