package com.islamversity.surah.mapper

import com.islamversity.core.Mapper
import com.islamversity.domain.model.aya.AyaRepoModel
import com.islamversity.surah.model.AyaUIModel

class AyaRepoUIMapper : Mapper<AyaRepoModel, AyaUIModel>{
    override fun map(item: AyaRepoModel): AyaUIModel =
        AyaUIModel(
            item.id.id,
            item.content,
            item.order,
            0,
            false,
        )
}