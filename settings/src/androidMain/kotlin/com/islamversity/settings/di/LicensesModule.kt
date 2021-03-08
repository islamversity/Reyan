package com.islamversity.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import com.islamversity.base.CoroutineView
import com.islamversity.core.mvi.MviPresenter
import com.islamversity.daggercore.CoreComponent
import com.islamversity.settings.databinding.ViewFosslicenseBinding
import kotlinx.coroutines.flow.Flow

@dagger.Module
object LicensesModule {
    @JvmStatic
    @dagger.Provides
    @FeatureScope
    fun providePresenter( processor: MviProcessor<LicensesIntent, LicensesResult>) :
            MviPresenter<LicensesIntent, LicesnsesState> = LicensesPresenter(processor)


    @JvmStatic
    @Provides
    fun provideProcessor(
        licensesRepo: LicensesRepo,
        mapper : Mapper<Calligraphy, CalligraphyUIModel>
    ): MviProcessor<LicensesIntent, LicensesResult> =
        SettingsProcessor(mapper, licensesRepo)
}