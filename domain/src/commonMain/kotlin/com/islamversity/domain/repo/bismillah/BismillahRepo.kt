package com.islamversity.domain.repo.bismillah

import com.islamversity.core.Mapper
import com.islamversity.core.mapWithNullable
import com.islamversity.db.datasource.BismillahLocalDataSource
import com.islamversity.db.model.Bismillah
import com.islamversity.domain.model.CalligraphyId
import com.islamversity.domain.model.surah.BismillahRepoModel
import com.islamversity.domain.model.surah.BismillahRepoType
import com.islamversity.domain.model.surah.toEntity
import com.islamversity.domain.model.toEntity
import kotlinx.coroutines.flow.Flow

interface BismillahRepo {

    fun getBismillah(
        type: BismillahRepoType,
        calligraphyId: CalligraphyId
    ): Flow<BismillahRepoModel?>
}

class BismillahRepoImpl(
    private val dataSource: BismillahLocalDataSource,
    private val mapper: Mapper<Bismillah, BismillahRepoModel>
) : BismillahRepo {
    override fun getBismillah(
        type: BismillahRepoType,
        calligraphyId: CalligraphyId
    ): Flow<BismillahRepoModel?> =
        dataSource.getBismillahWithFlag(type.toEntity(), calligraphyId.toEntity())
            .mapWithNullable(mapper)
}
