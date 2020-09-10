package com.islamversity.domain.mapper

import com.islamversity.core.Mapper
import com.islamversity.db.model.Aya
import com.islamversity.domain.model.aya.AyaID
import com.islamversity.domain.model.aya.AyaRepoModel

class AyaEntityRepoMapper : Mapper<Aya, AyaRepoModel> {
    override fun map(item: Aya): AyaRepoModel =
        AyaRepoModel(
            AyaID(item.id.id),
            item.content,
            item.order.order,
            item.juz.value,
            item.hizb.value
        )
}