package com.islamversity.quran_home.feature.surah.mapper

import com.islamversity.core.Mapper
import com.islamversity.domain.model.surah.SurahRepoModel
import com.islamversity.quran_home.feature.surah.model.SurahUIModel

class SurahRepoUIMapper : Mapper<SurahRepoModel, SurahUIModel> {

    override fun map(item: SurahRepoModel) =
        SurahUIModel(
            item.id.id,
            item.order,
            item.name,
            item.revealedType
        )
}