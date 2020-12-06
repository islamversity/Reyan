package com.islamversity.surah.mapper

import com.islamversity.core.Mapper
import com.islamversity.domain.model.aya.AyaRepoModel
import com.islamversity.domain.model.aya.SajdahTypeRepoModel
import com.islamversity.domain.model.aya.StartPartition
import com.islamversity.surah.model.AyaUIModel
import com.islamversity.surah.model.SajdahTypeUIModel

class AyaRepoUIMapper : Mapper<AyaRepoModel, AyaUIModel>{
    override fun map(item: AyaRepoModel): AyaUIModel =
        AyaUIModel(
            item.id.id,
            item.content,
            item.translation1,
            item.translation2,
            item.order,
            false,
            if(item.start == StartPartition.HIZB) item.hizb else null,
            if(item.start == StartPartition.JUZ) item.juz else null,
            mapSajdahType(item.sajdahType)
        )

    private fun mapSajdahType(sajdahTypeRepoModel: SajdahTypeRepoModel) : SajdahTypeUIModel =
        when(sajdahTypeRepoModel){
            SajdahTypeRepoModel.RECOMMENDED -> SajdahTypeUIModel.VISIBLE.RECOMMENDED
            SajdahTypeRepoModel.OBLIGATORY -> SajdahTypeUIModel.VISIBLE.OBLIGATORY
            SajdahTypeRepoModel.NONE -> SajdahTypeUIModel.NONE
        }
}