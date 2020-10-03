package com.islamversity.surah.mapper

import com.islamversity.core.Mapper
import com.islamversity.domain.model.surah.BismillahRepoType
import com.islamversity.domain.model.surah.SurahRepoModel
import com.islamversity.surah.model.SurahHeaderUIModel

class SurahRepoHeaderMapper : Mapper<SurahRepoModel, SurahHeaderUIModel> {
    override fun map(item: SurahRepoModel): SurahHeaderUIModel =
        SurahHeaderUIModel(
            rowId = item.id.id,
            number = item.order.toString(),
            name = item.arabicName,
            nameTranslated = item.mainName,
            origin = item.revealedType.rawName,
            verses = item.ayaCount.toInt(),
            fontSize = 20,
            showBismillah = item.bismillahType.isVisible(),
        )

    private fun BismillahRepoType.isVisible() =
        when(this){
            BismillahRepoType.FIRST_AYA,
            BismillahRepoType.NONE -> false
            BismillahRepoType.NEEDED -> true

        }
}