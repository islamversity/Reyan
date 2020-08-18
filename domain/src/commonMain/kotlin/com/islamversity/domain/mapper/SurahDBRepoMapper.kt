package com.islamversity.domain.mapper

import com.islamversity.core.Mapper
import com.islamversity.db.model.Surah
import com.islamversity.domain.model.SurahId
import com.islamversity.domain.model.SurahRepoModel
import com.islamversity.domain.model.toDomain

class SurahDBRepoMapper : Mapper<Surah, SurahRepoModel> {

    override fun map(item: Surah) =
        SurahRepoModel(
            item.index,
            SurahId(item.id.id),
            item.order.order,
            item.name,
            item.revealedType.toDomain(),
            item.bismillahType.toDomain()
        )
}