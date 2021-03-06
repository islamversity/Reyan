package com.islamversity.quran_home.feature.surah.mapper

import com.islamversity.core.Mapper
import com.islamversity.domain.model.surah.SurahRepoModel
import com.islamversity.quran_home.feature.surah.model.SurahUIModel

class SurahRepoUIMapper : Mapper<SurahRepoModel, SurahUIModel> {

    override fun map(item: SurahRepoModel): SurahUIModel =
        SurahUIModel(
            item.id,
            item.order,
            item.arabicName,
            item.mainName,
            item.revealedType,
            item.ayaCount
        )
}