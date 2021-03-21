package com.islamversity.settings.di

import com.islamversity.core.Mapper
import com.islamversity.core.mvi.MviPresenter
import com.islamversity.core.mvi.MviProcessor
import com.islamversity.daggercore.scope.FeatureScope
import com.islamversity.domain.repo.LicensesRepo
import com.islamversity.domain.model.LicensesRepoModel
import com.islamversity.settings.*
import com.islamversity.settings.models.LicenseDomainUIMapper
import com.islamversity.settings.models.LicensesUIModel
import dagger.*

@Module
object LicensesModule {

    @Provides
    @JvmStatic
    @FeatureScope
    fun providePresenter( processor: MviProcessor<LicensesIntent, LicensesResult>) :
            MviPresenter<LicensesIntent, LicensesState> = LicensesPresenter(processor)

    @Provides
    @JvmStatic
    fun provideCalligraphyDomainUIMapper(): Mapper<LicensesRepoModel, LicensesUIModel> =
        LicenseDomainUIMapper()

    @Provides
    @JvmStatic
    fun provideProcessor(
        licensesRepo: LicensesRepo,
        mapper : Mapper<LicensesRepoModel, LicensesUIModel>
    ): MviProcessor<LicensesIntent, LicensesResult> =
        LicensesProcessor(mapper, licensesRepo)
}