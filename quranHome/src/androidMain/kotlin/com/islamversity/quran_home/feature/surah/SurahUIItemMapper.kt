package com.islamversity.quran_home.feature.surah

import com.islamversity.core.Mapper
import com.islamversity.quran_home.feature.surah.model.SurahUIModel
import com.islamversity.view_component.SurahItemModel

class SurahUIItemMapper : Mapper<SurahUIModel, SurahItemModel> {
    override fun map(item: SurahUIModel): SurahItemModel =
        SurahItemModel(
            item.id.id,
            item.arabicName,
            item.mainName,
            item.order.toString(),
            SurahItemModel.RevealedType(item.revealedType.rawName),
            item.ayaCount.toString()
        )
}
