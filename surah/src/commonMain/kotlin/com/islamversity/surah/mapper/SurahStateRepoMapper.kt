package com.islamversity.surah.mapper

import com.islamversity.core.Mapper
import com.islamversity.domain.model.surah.SurahStateRepoModel
import com.islamversity.navigation.model.SurahLocalModel

class SurahStateRepoMapper : Mapper<SurahLocalModel.FullSurah, SurahStateRepoModel> {

    override fun map(item: SurahLocalModel.FullSurah) =
        SurahStateRepoModel(item.surahName,item.surahID,item.startingAyaOrder)
}