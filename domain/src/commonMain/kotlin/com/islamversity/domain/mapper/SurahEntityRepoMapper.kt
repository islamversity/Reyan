package com.islamversity.domain.mapper

import com.islamversity.core.Mapper
import com.islamversity.db.model.RevealType
import com.islamversity.db.model.Surah
import com.islamversity.domain.model.surah.RevealedType
import com.islamversity.domain.model.surah.SurahID
import com.islamversity.domain.model.surah.SurahRepoModel
import com.islamversity.domain.model.surah.toRepo

class SurahEntityRepoMapper : Mapper<Surah, SurahRepoModel> {
    override fun map(item: Surah): SurahRepoModel =
        SurahRepoModel(
            id = SurahID(item.id.id),
            order = item.order.order.toInt(),
            name = item.name,
            revealedType = when(item.revealedType){
                is RevealType.MECCAN -> RevealedType.MECCAN(item.revealedType.name)
                is RevealType.MEDINAN -> RevealedType.MEDINAN(item.revealedType.name)
            },
            bismillahType = item.bismillahType.toRepo()
        )
}
