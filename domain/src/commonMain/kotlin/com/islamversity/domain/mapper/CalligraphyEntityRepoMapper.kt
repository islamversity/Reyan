package com.islamversity.domain.mapper

import com.islamversity.core.Mapper
import com.islamversity.domain.model.Calligraphy
import com.islamversity.domain.model.CalligraphyId
import com.islamversity.domain.model.CalligraphyName
import com.islamversity.domain.model.Language
import com.islamversity.db.Calligraphy as CalligraphyEntity

class CalligraphyEntityRepoMapper : Mapper<CalligraphyEntity, Calligraphy> {
    override fun map(item: CalligraphyEntity): Calligraphy =
        Calligraphy(
            CalligraphyId(item.id.id),
            Language(item.languageCode.lang),
            item.name?.name?.let { CalligraphyName(it) },
            item.friendlyName
        )
}