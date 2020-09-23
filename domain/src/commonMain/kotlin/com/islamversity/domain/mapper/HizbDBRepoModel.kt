package com.islamversity.domain.mapper

import com.islamversity.core.Mapper
import com.islamversity.db.model.HizbEntity
import com.islamversity.domain.model.HizbRepoModel

class HizbDBRepoModel : Mapper<HizbEntity, HizbRepoModel> {
    override fun map(item: HizbEntity): HizbRepoModel =
        HizbRepoModel(
            item.startingAyaId.id,
            item.startingAyaOrder.order,
            item.startingSurahId.id,
            item.startingSurahName,

            item.endingAyaId.id,
            item.endingAyaOrder.order,
            item.endingSurahId.id,
            item.endingSurahName,

            item.juz.value,
            item.hizb.value,
        )
}