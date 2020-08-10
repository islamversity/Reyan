package com.islamversity.quran_home.mapper

import com.islamversity.core.Mapper
import com.islamversity.domain.model.surah.SurahRepoModel
import com.islamversity.quran_home.model.SurahUIModel

class SurahRepoUIMapper : Mapper<SurahRepoModel, SurahUIModel> {

    override fun map(item: SurahRepoModel) =
        SurahUIModel(
            id = item.id,
            order = item.order,
            name = item.name,
            revealedType = item.revealedType
        )
}