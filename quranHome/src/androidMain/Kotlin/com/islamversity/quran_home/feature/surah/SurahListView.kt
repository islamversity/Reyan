package com.islamversity.quran_home.feature.surah

import android.view.LayoutInflater
import android.view.ViewGroup
import com.islamversity.base.CoroutineView
import com.islamversity.core.mvi.MviPresenter
import com.islamversity.daggercore.CoreComponent
import com.islamversity.daggercore.lifecycleComponent
import com.islamversity.daggercore.navigator.DaggerDefaultNavigationComponent
import com.islamversity.quran_home.R
import com.islamversity.quran_home.databinding.SurahListViewBinding
import com.islamversity.quran_home.feature.setDivider
import com.islamversity.quran_home.feature.surah.di.DaggerSurahListComponent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

/**
 * @author Ali (alirezaiyann@gmail.com)
 * @since 9/3/2020 6:18 PM.
 */
class SurahListView : CoroutineView<SurahListViewBinding, SurahListState, SurahListIntent>() {

    private val intentChannel = Channel<SurahListIntent>(Channel.UNLIMITED)

    @Inject
    override lateinit var presenter: MviPresenter<SurahListIntent, SurahListState>

    override fun injectDependencies(core: CoreComponent) {
        DaggerSurahListComponent
            .builder()
            .coreComponent(core)
            .lifecycleComponent(activity!!.lifecycleComponent)
            .navComponent(DaggerDefaultNavigationComponent.factory().create(core, parentController!!.router))
            .build()
            .inject(this)
    }

    override fun beforeBindingView(binding: SurahListViewBinding) {
        binding.epoxyView.setDivider(R.drawable.surah_divider)
    }
    override fun render(state: SurahListState) {
        binding.epoxyView.withModelsAsync {
            state.surahList.forEach {
                surahView {
                    id(it.index)
                    surah(it)
                    listener {
                        intentChannel.offer(SurahListIntent.ItemClick(SurahRowActionModel(it)))
                    }
                }
            }
        }
    }

    override fun intents(): Flow<SurahListIntent> =
        listOf(flowOf(SurahListIntent.Initial), intentChannel.receiveAsFlow()).merge()

    override fun bindView(inflater: LayoutInflater, container: ViewGroup): SurahListViewBinding =
        SurahListViewBinding.inflate(inflater, container, false)
}