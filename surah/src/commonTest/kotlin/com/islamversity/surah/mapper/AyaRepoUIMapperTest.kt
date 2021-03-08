package com.islamversity.surah.mapper
import com.islamversity.domain.model.aya.AyaID
import com.islamversity.domain.model.aya.AyaRepoModel
import com.islamversity.domain.model.aya.SajdahTypeRepoModel
import com.islamversity.domain.model.aya.StartPartition
import com.islamversity.domain.model.surah.SurahID
import com.islamversity.surah.model.AyaUIModel
import kotlin.test.Test
import kotlin.test.assertEquals

class AyaRepoUIMapperTest {

    val mapper = AyaRepoUIMapper()

    @Test
    fun `maps Hizb 1 correctly to Beginning`(){
        val repoModel = AyaRepoModel(
            id = AyaID(id = "1"),
            content = "content",
            translation1 = null,
            translation2 = null,
            order = 0,
            juz = 2,
            hizb = 9,
            sajdahType = SajdahTypeRepoModel.NONE,
            start = StartPartition.JUZ,
            surahId = SurahID(id = "")
        )

        val uiModel = mapper.map(repoModel)

        assertEquals(2, uiModel.juz)
        assertEquals(AyaUIModel.HizbProgress.Beginning(3), uiModel.hizb)
    }

    @Test
    fun `maps Hizb 2 correctly to Quarter`(){
        val repoModel = AyaRepoModel(
            id = AyaID(id = "1"),
            content = "content",
            translation1 = null,
            translation2 = null,
            order = 0,
            juz = 0,
            hizb = 10,
            sajdahType = SajdahTypeRepoModel.NONE,
            start = StartPartition.HIZB,
            surahId = SurahID(id = "")
        )

        val uiModel = mapper.map(repoModel)

        assertEquals(AyaUIModel.HizbProgress.Quarter(3), uiModel.hizb)
    }

    @Test
    fun `maps Hizb 3 correctly to Half`(){
        val repoModel = AyaRepoModel(
            id = AyaID(id = "1"),
            content = "content",
            translation1 = null,
            translation2 = null,
            order = 0,
            juz = 0,
            hizb = 11,
            sajdahType = SajdahTypeRepoModel.NONE,
            start = StartPartition.HIZB,
            surahId = SurahID(id = "")
        )

        val uiModel = mapper.map(repoModel)

        assertEquals(AyaUIModel.HizbProgress.Half(3), uiModel.hizb)
    }

    @Test
    fun `maps Hizb 4 correctly to ThreeFourth`(){
        val repoModel = AyaRepoModel(
            id = AyaID(id = "1"),
            content = "content",
            translation1 = null,
            translation2 = null,
            order = 0,
            juz = 0,
            hizb = 12,
            sajdahType = SajdahTypeRepoModel.NONE,
            start = StartPartition.HIZB,
            surahId = SurahID(id = "")
        )

        val uiModel = mapper.map(repoModel)

        assertEquals(AyaUIModel.HizbProgress.ThreeFourth(3), uiModel.hizb)
    }
}