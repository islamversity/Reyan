package com.islamversity.domain.mapper

import com.islamversity.core.Mapper
import com.islamversity.db.model.SoraEntityModel
import com.islamversity.domain.model.sora.SoraRepoModel

class SoraEntityRepoMapper : Mapper<SoraEntityModel, SoraRepoModel> {
    override fun map(item: SoraEntityModel): SoraRepoModel =
        SoraRepoModel(
            id = item.uuid,
            index = item.index,
            name = item.name
        )
}
