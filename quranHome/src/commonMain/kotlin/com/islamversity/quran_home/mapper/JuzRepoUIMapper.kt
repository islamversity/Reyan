package com.islamversity.quran_home.mapper

import com.islamversity.core.Mapper
import com.islamversity.domain.model.JuzRepoModel
import com.islamversity.quran_home.model.JozUIModel

class JuzRepoUIMapper : Mapper<JuzRepoModel, JozUIModel> {

    override fun map(item: JuzRepoModel) =
        JozUIModel(item.juz)
}