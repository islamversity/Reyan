package com.islamversity.quran_home.feature.surah

import android.view.LayoutInflater
import android.view.ViewGroup
import com.islamversity.base.CoroutineView
import com.islamversity.core.Mapper
import com.islamversity.core.mvi.MviPresenter
import com.islamversity.daggercore.CoreComponent
import com.islamversity.daggercore.lifecycleComponent
import com.islamversity.daggercore.navigator.DaggerDefaultNavigationComponent
import com.islamversity.quran_home.R
import com.islamversity.quran_home.databinding.SurahListViewBinding
import com.islamversity.quran_home.feature.surah.di.DaggerSurahListComponent
import com.islamversity.quran_home.feature.surah.model.SurahUIModel
import com.islamversity.view_component.SurahItemModel
import com.islamversity.view_component.surahItem
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

class SurahListView : CoroutineView<SurahListViewBinding, SurahListState, SurahListIntent>() {

    private val intentChannel = Channel<SurahListIntent>(Channel.UNLIMITED)

    @Inject
    override lateinit var presenter: MviPresenter<SurahListIntent, SurahListState>

    @Inject
    lateinit var surahMapper : Mapper<SurahUIModel, SurahItemModel>

    override fun bindView(inflater: LayoutInflater, container: ViewGroup): SurahListViewBinding =
        SurahListViewBinding.inflate(inflater, container, false)

    override fun injectDependencies(core: CoreComponent) {
        DaggerSurahListComponent
            .builder()
            .coreComponent(core)
            .lifecycleComponent(activity!!.lifecycleComponent)
            .navComponent(DaggerDefaultNavigationComponent.factory().create(core, parentController!!.router))
            .build()
            .inject(this)
    }

    override fun render(state: SurahListState) {
        binding.epoxyView.withModelsAsync {
            state.surahList.forEach {model ->
                surahItem {
                    id(model.id.id)
                    uiItem(surahMapper.map(model))
                    listener {
                        intentChannel.offer(SurahListIntent.ItemClick(SurahRowActionModel(model)))
                    }
                }
            }
        }
    }

    override fun intents(): Flow<SurahListIntent> =
        listOf(flowOf(SurahListIntent.Initial), intentChannel.receiveAsFlow()).merge()
}