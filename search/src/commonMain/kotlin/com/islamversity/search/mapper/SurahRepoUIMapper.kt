package com.islamversity.search.mapper

import com.islamversity.core.Mapper
import com.islamversity.domain.model.surah.RevealedType
import com.islamversity.domain.model.surah.SurahRepoModel
import com.islamversity.search.model.SurahUIModel

class SurahRepoUIMapper(
) : Mapper<SurahRepoModel, SurahUIModel> {
    override fun map(item: SurahRepoModel): SurahUIModel =
        SurahUIModel(
            id = item.id,
            order = item.order,
            arabicName = item.arabicName,
            mainName = item.mainName,
            revealedType = item.revealedType,
            ayaCount = item.ayaCount
        )
}