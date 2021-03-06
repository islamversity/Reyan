package com.islamversity.domain.mapper

import com.islamversity.core.Mapper
import com.islamversity.core.listMap
import com.islamversity.db.model.HizbEntity
import com.islamversity.db.model.JuzEntity
import com.islamversity.domain.model.HizbRepoModel
import com.islamversity.domain.model.JuzRepoModel

class JuzDBRepoMapper(
    private val hizbMapper : Mapper<HizbEntity, HizbRepoModel>
) : Mapper<JuzEntity, JuzRepoModel> {

    override fun map(item: JuzEntity) =
        JuzRepoModel(
            item.startingAyaId.id,
            item.startingAyaOrder.order,
            item.startingSurahId.id,
            item.startingSurahName,

            item.endingAyaId.id,
            item.endingAyaOrder.order,
            item.endingSurahId.id,
            item.endingSurahName,

            item.juzOrderIndex.value,
            hizbMapper.listMap(item.hizbs)
        )
}