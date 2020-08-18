package com.islamversity.quran_home.mapper

import com.islamversity.core.Mapper
import com.islamversity.domain.model.SurahRepoModel
import com.islamversity.quran_home.model.SurahUIModel

class SurahRepoUIMapper : Mapper<SurahRepoModel, SurahUIModel> {

    override fun map(item: SurahRepoModel) =
        SurahUIModel(
            item.index,
            item.id.id,
            item.order,
            item.name,
            item.revealedType,
            item.bismillahType
        )
}