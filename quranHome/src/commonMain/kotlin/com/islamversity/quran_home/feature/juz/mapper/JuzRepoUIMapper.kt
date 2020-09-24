package com.islamversity.quran_home.feature.juz.mapper

import com.islamversity.core.Mapper
import com.islamversity.domain.model.JuzRepoModel
import com.islamversity.quran_home.feature.juz.model.JozUIModel

class JuzRepoUIMapper : Mapper<JuzRepoModel, JozUIModel> {

    override fun map(item: JuzRepoModel) =
        JozUIModel(
            item.juz,
            "${item.startingSurahName}: ${item.startingAyaOrder}",
            "${item.endingSurahName}: ${item.endingAyaOrder}"
        )

}