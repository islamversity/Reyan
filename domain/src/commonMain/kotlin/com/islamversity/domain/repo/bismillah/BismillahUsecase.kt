package com.islamversity.domain.repo.bismillah

import com.islamversity.domain.model.surah.BismillahRepoModel
import com.islamversity.domain.model.surah.BismillahRepoType
import com.islamversity.domain.repo.SettingRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow

interface BismillahUsecase {

    fun getBismillahWithType(type: BismillahRepoType): Flow<BismillahRepoModel?>
}

class BismillahUsecaseImpl(
    private val repo: BismillahRepo,
    private val settingRepo: SettingRepo
) : BismillahUsecase {
    override fun getBismillahWithType(type: BismillahRepoType): Flow<BismillahRepoModel?> =
        settingRepo.getCurrentQuranReadCalligraphy()
            .flatMapMerge {
                repo.getBismillah(type, it.id)
            }
}