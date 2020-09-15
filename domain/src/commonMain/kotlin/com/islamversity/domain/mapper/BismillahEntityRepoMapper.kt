package com.islamversity.domain.mapper

import com.islamversity.core.Mapper
import com.islamversity.db.model.Bismillah
import com.islamversity.domain.model.surah.BismillahRepoModel

class BismillahEntityRepoMapper : Mapper<Bismillah, BismillahRepoModel> {
    override fun map(item: Bismillah): BismillahRepoModel =
        BismillahRepoModel(
            item.id.id,
            item.name
        )
}