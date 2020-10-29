package com.islamversity.domain.mapper

import com.islamversity.core.Mapper
import com.islamversity.db.model.Aya
import com.islamversity.db.model.SajdahTypeFlag
import com.islamversity.domain.model.aya.AyaID
import com.islamversity.domain.model.aya.AyaRepoModel
import com.islamversity.domain.model.aya.SajdahTypeRepoModel

class AyaEntityRepoMapper : Mapper<Aya, AyaRepoModel> {
    override fun map(item: Aya): AyaRepoModel =
        AyaRepoModel(
            AyaID(item.id.id),
            item.content,
            item.translation1,
            item.translation2,
            item.order.order,
            item.juz.value,
            item.hizb.value,
            mapSajdahType(item.sajdahType)
        )

    private fun mapSajdahType(sajdahTypeFlag: SajdahTypeFlag?) : SajdahTypeRepoModel =
        when(sajdahTypeFlag){
            null -> SajdahTypeRepoModel.NONE
            SajdahTypeFlag.RECOMMENDED -> SajdahTypeRepoModel.RECOMMENDED
            SajdahTypeFlag.OBLIGATORY -> SajdahTypeRepoModel.OBLIGATORY
        }
}