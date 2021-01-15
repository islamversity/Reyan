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
            mapHizbNumber(item.start, item.hizb),
            if(item.start == StartPartition.JUZ) item.juz else null,
            mapSajdahType(item.sajdahType)
        )

    private fun mapSajdahType(sajdahTypeRepoModel: SajdahTypeRepoModel) : SajdahTypeUIModel =
        when(sajdahTypeRepoModel){
            SajdahTypeRepoModel.RECOMMENDED -> SajdahTypeUIModel.VISIBLE.RECOMMENDED
            SajdahTypeRepoModel.OBLIGATORY -> SajdahTypeUIModel.VISIBLE.OBLIGATORY
            SajdahTypeRepoModel.NONE -> SajdahTypeUIModel.NONE
        }

    /**
     * We have 2 Hizbs in every Juz
     * and each hizb has 4 part
     * for example: Juz first has 1 to 16 part of Hizb
     * and first aya shows the begining of the Juz and Hizb
     * aya at part 5 of Hizb shows the second Hizb of the Juz
     *
     * Juz | Hizb | presentation
     * 1   |  1   |      Juz = 1 or Hizb = 1
     * 1   |  2   |      Hizb = 1 or 1/4
     * 1   |  3   |      Hizb = 1 or 1/2
     * 1   |  4   |      Hizb = 1 or 3/4
     * 1   |  5   |                 Hizb = 2
     * 1   |  6   |      Hizb = 2 or 1/4
     * 1   |  7   |      Hizb = 2 or 1/2
     * 1   |  8   |      Hizb = 2 or 3/4
     * 2   |  9   |      Juz = 2 or Hizb = 3
     * 2   |  10  |      Hizb = 3 or 1/4
     * 2   |  11  |      Hizb = 3 or 1/2
     * 2   |  12  |      Hizb = 3 or 3/4
     * 2   |  13  |                 Hizb = 4
     * 2   |  14  |      Hizb = 4 or 1/4
     * 2   |  15  |      Hizb = 4 or 1/2
     * 2   |  16  |      Hizb = 4 or 3/4
     * 3   |  17  |     Juz = 3 or Hizb = 5
     */
    private fun mapHizbNumber(startPosition : StartPartition?, rawHizb : Long) : Long? {
        if(startPosition != StartPartition.HIZB) return null

        val remainder = rawHizb % NUMBER_OF_HIZB_PARTS

        if(remainder == 0L) return rawHizb / NUMBER_OF_HIZB_PARTS

        return (rawHizb + NUMBER_OF_HIZB_PARTS - remainder) / NUMBER_OF_HIZB_PARTS
    }
}

private const val NUMBER_OF_HIZB_PARTS = 4L
