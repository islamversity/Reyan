package com.islamversity.surah.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.islamversity.base.CoroutineView
import com.islamversity.core.mvi.MviPresenter
import com.islamversity.daggercore.CoreComponent
import com.islamversity.navigation.fromByteArray
import com.islamversity.navigation.model.SurahLocalModel
import com.islamversity.surah.SurahIntent
import com.islamversity.surah.SurahState
import com.islamversity.surah.databinding.ViewSurahBinding
import com.islamversity.surah.di.DaggerSurahComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class SurahView(
    bundle: Bundle
) : CoroutineView<ViewSurahBinding, SurahState, SurahIntent>(bundle) {

    private val surahLocal: SurahLocalModel =
        bundle
            .getByteArray(SurahLocalModel.EXTRA_SURAH_DETAIL)!!
            .let {
                SurahLocalModel.fromByteArray(it)
            }

    @Inject
    override lateinit var presenter: MviPresenter<SurahIntent, SurahState>

    override fun bindView(inflater: LayoutInflater, container: ViewGroup): ViewSurahBinding =
        ViewSurahBinding.inflate(inflater, container, false)

    override fun injectDependencies(core: CoreComponent) {
        DaggerSurahComponent
            .builder()
            .bindRouter(router)
            .coreComponent(core)
            .build()
            .inject(this)
    }

    override fun beforeBindingView(binding: ViewSurahBinding) {
    }

    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
    }

    override fun intents(): Flow<SurahIntent> =
        flowOf(
            SurahIntent.Initial(
                surahLocal.surahID,
                surahLocal.startingAyaOrder,
                surahLocal.bismillahType
            )
        )

    override fun render(state: SurahState) {
        renderLoading(state.base)
        renderError(state.base)

        //render bismillah

        binding.ayaList.withModelsAsync {
            state.ayas.forEach {
                ayaView {
                    id(it.id)
                    model(it)
                }
            }
        }
    }
}