package com.islamversity.surah.model

import com.islamversity.core.Mapper
import com.islamversity.domain.model.Calligraphy

class CalligraphyDomainUIMapper : Mapper<Calligraphy, CalligraphyUIModel> {
    override fun map(item: Calligraphy) = CalligraphyUIModel(
        item.id.id,
        item.friendlyName
    )
}