package com.islamversity.search.mapper

import com.islamversity.core.Mapper
import com.islamversity.domain.model.sora.SoraRepoModel
import com.islamversity.domain.model.sora.SoraUIModel

class SoraRepoUIMapper(
) : Mapper<SoraRepoModel, SoraUIModel> {
    override fun map(item: SoraRepoModel): SoraUIModel =
        SoraUIModel(
            id = item.id,
            index = item.index,
            name = item.name
        )
}