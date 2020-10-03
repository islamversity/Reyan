package com.islamversity.surah.di

import com.islamversity.core.Mapper
import com.islamversity.core.mvi.MviPresenter
import com.islamversity.core.mvi.MviProcessor
import com.islamversity.daggercore.scope.FeatureScope
import com.islamversity.domain.model.aya.AyaRepoModel
import com.islamversity.domain.model.surah.SurahRepoModel
import com.islamversity.domain.repo.SettingRepo
import com.islamversity.domain.repo.aya.GetAyaUseCase
import com.islamversity.domain.repo.surah.GetSurahUsecase
import com.islamversity.navigation.Navigator
import com.islamversity.surah.SurahIntent
import com.islamversity.surah.SurahPresenter
import com.islamversity.surah.SurahProcessor
import com.islamversity.surah.SurahResult
import com.islamversity.surah.SurahState
import com.islamversity.surah.mapper.AyaRepoUIMapper
import com.islamversity.surah.mapper.SurahRepoHeaderMapper
import com.islamversity.surah.model.AyaUIModel
import com.islamversity.surah.model.SurahHeaderUIModel
import dagger.Module
import dagger.Provides

@Module
object SurahModule {

    @Provides
    @JvmStatic
    fun bindProcessor(
        navigator: Navigator,
        getAyaUseCase: GetAyaUseCase,
        mapper: Mapper<AyaRepoModel, AyaUIModel>,
        settings: SettingRepo,
        surahUsecase: GetSurahUsecase,
        surahRepoHeaderMapper: Mapper<SurahRepoModel, SurahHeaderUIModel>
    ): MviProcessor<SurahIntent, SurahResult> =
        SurahProcessor(
            navigator = navigator,
            getAyaUseCase = getAyaUseCase,
            ayaMapper = mapper,
            settingRepo = settings,
            surahUsecase = surahUsecase,
            surahRepoHeaderMapper = surahRepoHeaderMapper,
        )

    @FeatureScope
    @Provides
    @JvmStatic
    fun bindPresenter(
        processor: MviProcessor<SurahIntent, SurahResult>
    ): MviPresenter<SurahIntent, SurahState> = SurahPresenter(
        processor
    )

    @Provides
    @JvmStatic
    fun provideAyaRepoUIMapper(): Mapper<AyaRepoModel, AyaUIModel> = AyaRepoUIMapper()

    @Provides
    @JvmStatic
    fun provideSurahRepoHeaderMapper(): Mapper<SurahRepoModel, SurahHeaderUIModel> = SurahRepoHeaderMapper()
}