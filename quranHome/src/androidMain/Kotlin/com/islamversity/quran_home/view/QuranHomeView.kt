package com.islamversity.quran_home.view

import android.view.LayoutInflater
import android.view.ViewGroup
import com.islamversity.base.CoroutineView
import com.islamversity.core.mvi.MviPresenter
import com.islamversity.daggercore.CoreComponent
import com.islamversity.daggercore.lifecycleComponent
import com.islamversity.daggercore.navigator.DaggerDefaultNavigationComponent
import com.islamversity.quran_home.JozRowActionModel
import com.islamversity.quran_home.QuranHomeIntent
import com.islamversity.quran_home.QuranHomeState
import com.islamversity.quran_home.SurahRowActionModel
import com.islamversity.quran_home.databinding.QuranHomeViewBinding
import com.islamversity.quran_home.di.DaggerQuranHomeComponent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import org.jetbrains.annotations.NotNull
import javax.inject.Inject
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration


class QuranHomeView : CoroutineView<QuranHomeViewBinding, QuranHomeState, QuranHomeIntent>() {

    private val intentChannel = Channel<QuranHomeIntent>(Channel.UNLIMITED)

    @Inject
    override lateinit var presenter: MviPresenter<QuranHomeIntent, QuranHomeState>

    override fun bindView(
        inflater: LayoutInflater,
        container: ViewGroup
    ): @NotNull QuranHomeViewBinding {
        return QuranHomeViewBinding.inflate(inflater, container, false)
    }

    override fun injectDependencies(core: CoreComponent) {
        DaggerQuranHomeComponent
            .builder()
            .bindRouter(router)
            .coreComponent(core)
            .lifecycleComponent(activity!!.lifecycleComponent)
            .navComponent(DaggerDefaultNavigationComponent.factory().create(core, router))
            .build()
            .inject(this)

    }

    override fun render(state: QuranHomeState) {
        renderLoading(state.base)
        renderError(state.base)
        renderList(state)
    }

    private fun renderList(state: QuranHomeState) {
        binding.rvSurah.withModelsAsync {
            state.surahList.forEach {
                surahView {
                    id(it.index)
                    surah(it)
                    listener {
                        intentChannel.offer(QuranHomeIntent.ItemClick(SurahRowActionModel(it)))
                    }
                }
            }
        }
        binding.rvJuz.withModelsAsync {
            state.juzList.forEach {
                juzView {
                    id(it.number)
                    juz(it)
                    listener {
                        intentChannel.offer(QuranHomeIntent.JozItemClick(JozRowActionModel(it)))
                    }
                }
            }
        }
    }

    @ExperimentalTime
    override fun intents(): Flow<QuranHomeIntent> {
        return listOf(flowOf(QuranHomeIntent.Initial), intentChannel.receiveAsFlow()).merge()

    }
}
