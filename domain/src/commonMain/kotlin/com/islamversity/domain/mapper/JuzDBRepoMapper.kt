package com.islamversity.domain.mapper

import com.islamversity.core.Mapper
import com.islamversity.db.model.JuzEntity
import com.islamversity.db.model.Surah
import com.islamversity.domain.model.JuzRepoModel
import com.islamversity.domain.model.SurahRepoModel

class JuzDBRepoMapper : Mapper<JuzEntity, JuzRepoModel> {

    override fun map(item: JuzEntity) =
        JuzRepoModel(
            item.ayaId.id,
            item.surahId.id,
            item.juz.value
        )
}