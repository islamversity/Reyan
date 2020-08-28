package com.islamversity.domain.mappers

import com.islamversity.core.Mapper
import com.islamversity.domain.model.Calligraphy
import com.islamversity.domain.model.CalligraphyId
import com.islamversity.domain.model.CalligraphyName
import com.islamversity.domain.model.Language
import com.islamversity.db.Calligraphy as CalligraphyEntity

class CalligraphyEntityDomainMapper : Mapper<CalligraphyEntity, Calligraphy> {
    override fun map(item: CalligraphyEntity): Calligraphy =
        Calligraphy(
            CalligraphyId(item.id.id),
            Language(item.languageCode.lang),
            item.name?.name?.let { CalligraphyName(it) },
            item.friendlyName
        )
}