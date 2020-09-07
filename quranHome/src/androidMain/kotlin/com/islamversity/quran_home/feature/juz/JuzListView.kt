package com.islamversity.quran_home.feature.juz

import android.view.LayoutInflater
import android.view.ViewGroup
import com.islamversity.base.CoroutineView
import com.islamversity.core.mvi.MviPresenter
import com.islamversity.daggercore.CoreComponent
import com.islamversity.daggercore.lifecycleComponent
import com.islamversity.daggercore.navigator.DaggerDefaultNavigationComponent
import com.islamversity.quran_home.databinding.JuzListViewBinding
import com.islamversity.quran_home.feature.juz.di.DaggerJuzListComponent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

class JuzListView : CoroutineView<JuzListViewBinding, JuzListState, JuzListIntent>() {

    private val intentChannel = Channel<JuzListIntent>(Channel.UNLIMITED)

    @Inject
    override lateinit var presenter: MviPresenter<JuzListIntent, JuzListState>

    override fun bindView(inflater: LayoutInflater, container: ViewGroup): JuzListViewBinding =
        JuzListViewBinding.inflate(inflater, container, false)

    override fun injectDependencies(core: CoreComponent) {
        DaggerJuzListComponent
            .builder()
            .coreComponent(core)
            .lifecycleComponent(activity!!.lifecycleComponent)
            .navComponent(DaggerDefaultNavigationComponent.factory().create(core, parentController!!.router))
            .build()
            .inject(this)
    }

    override fun render(state: JuzListState) {
        binding.epoxyView.withModelsAsync {
            state.juzList.forEach {
                juzView {
                    id(it.number)
                    juz(it)
                    listener {
                        intentChannel.offer(JuzListIntent.ItemClick(JozRowActionModel(it)))
                    }
                }
            }
        }
    }

    override fun intents(): Flow<JuzListIntent> =
        listOf(flowOf(JuzListIntent.Initial), intentChannel.receiveAsFlow()).merge()
}
