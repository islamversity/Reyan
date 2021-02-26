package com.islamversity.quran_home.feature.home.mapper

import com.islamversity.core.Mapper
import com.islamversity.domain.model.surah.SurahStateRepoModel
import com.islamversity.navigation.model.SurahLocalModel
import com.islamversity.quran_home.feature.home.SavedSurahState

class SurahStateUiMapper : Mapper<SurahStateRepoModel?, SavedSurahState?> {

    override fun map(item: SurahStateRepoModel?) =
        if (item != null) SavedSurahState(item.surahName, item.surahID, item.startingAyaOrder)
        else null
}