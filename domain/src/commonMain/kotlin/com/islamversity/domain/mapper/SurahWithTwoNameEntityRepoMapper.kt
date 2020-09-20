package com.islamversity.domain.mapper

import com.islamversity.core.Mapper
import com.islamversity.db.model.RevealTypeFlag
import com.islamversity.db.model.SurahWithTwoName
import com.islamversity.domain.model.surah.RevealedType
import com.islamversity.domain.model.surah.SurahID
import com.islamversity.domain.model.surah.SurahRepoModel
import com.islamversity.domain.model.surah.toRepo

class SurahWithTwoNameEntityRepoMapper : Mapper<SurahWithTwoName, SurahRepoModel> {
    override fun map(item: SurahWithTwoName): SurahRepoModel =
        SurahRepoModel(
            id = SurahID(item.id.id),
            order = item.order.order.toInt(),
            arabicName = item.arabicName,
            mainName = item.mainName,
            revealedType = when(item.revealedType){
                RevealTypeFlag.MECCAN -> RevealedType.MECCAN
                RevealTypeFlag.MEDINAN -> RevealedType.MEDINAN
            },
            bismillahType = item.bismillahType.toRepo(),
            ayaCount = item.ayaCount,
        )
}
