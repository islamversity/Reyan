package com.islamversity.quran_home.feature.home

import android.animation.ValueAnimator
import android.view.LayoutInflater
import android.view.ViewGroup
import com.islamversity.base.CoroutineView
import com.islamversity.core.mvi.MviPresenter
import com.islamversity.daggercore.CoreComponent
import com.islamversity.daggercore.lifecycleComponent
import com.islamversity.daggercore.navigator.DaggerDefaultNavigationComponent
import com.islamversity.quran_home.databinding.QuranHomeViewBinding
import com.islamversity.quran_home.feature.home.di.DaggerQuranHomeComponent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.receiveAsFlow
import org.jetbrains.annotations.NotNull
import javax.inject.Inject
import kotlin.time.ExperimentalTime


class QuranHomeView : CoroutineView<QuranHomeViewBinding, QuranHomeState, QuranHomeIntent>() {

    private val intentChannel = Channel<QuranHomeIntent>(Channel.UNLIMITED)
    private val pagerAdapter: HomePagerAdapter by lazy {
        HomePagerAdapter(this)
    }

    @Inject
    override lateinit var presenter: MviPresenter<QuranHomeIntent, QuranHomeState>

    override fun bindView(
        inflater: LayoutInflater,
        container: ViewGroup
    ): @NotNull QuranHomeViewBinding {
        return QuranHomeViewBinding.inflate(inflater, container, false)
    }

    override fun beforeBindingView(binding: QuranHomeViewBinding) {
        super.beforeBindingView(binding)
        binding.inputCard.setOnClickListener {
            ValueAnimator.ofFloat(binding.motionLayout.progress, 0F).apply {
                duration = 300
                addUpdateListener {
                    binding.motionLayout.progress = it.animatedValue as Float
                }
            }.start()

        }
        binding.viewPager.adapter = pagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
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
        binding.viewPager.currentItem = state.tabPosition
    }

    @ExperimentalTime
    override fun intents(): Flow<QuranHomeIntent> {
        return listOf(flowOf(QuranHomeIntent.Initial), intentChannel.receiveAsFlow()).merge()

    }
}
