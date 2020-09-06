package com.islamversity.domain.repo.aya

import com.islamversity.core.Mapper
import com.islamversity.core.mapListWith
import com.islamversity.db.datasource.AyaLocalDataSource
import com.islamversity.db.model.Aya
import com.islamversity.domain.model.CalligraphyId
import com.islamversity.domain.model.aya.AyaRepoModel
import com.islamversity.domain.model.surah.SurahID
import com.islamversity.domain.model.surah.toEntity
import com.islamversity.domain.model.toEntity
import kotlinx.coroutines.flow.Flow

interface AyaListRepo {

    fun observeAllAyas(id : SurahID, calligraphyId: CalligraphyId) : Flow<List<AyaRepoModel>>
}

class AyaListRepoImpl(
    private val dataSource: AyaLocalDataSource,
    private val ayaMapper : Mapper<Aya, AyaRepoModel>
) : AyaListRepo{

    override fun observeAllAyas(
        id: SurahID,
        calligraphyId: CalligraphyId
    ): Flow<List<AyaRepoModel>> =
        dataSource.observeAllAyasForSora(id.toEntity(), calligraphyId.toEntity())
            .mapListWith(ayaMapper)
}