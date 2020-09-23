package com.islamversity.domain.repo.juz

import com.islamversity.domain.model.JuzRepoModel
import com.islamversity.domain.repo.SettingRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest

interface JuzListUsecase {

    fun getAllJuz(): Flow<List<JuzRepoModel>>
}

class JuzListUsecaseImpl(
    private val juzListRepo: JuzListRepo,
    private val settingRepo: SettingRepo,
) : JuzListUsecase {

    override fun getAllJuz(): Flow<List<JuzRepoModel>> =
        settingRepo.getCurrentSurahCalligraphy()
            .flatMapLatest {
                juzListRepo.getAllJuz(it.id)
            }
}