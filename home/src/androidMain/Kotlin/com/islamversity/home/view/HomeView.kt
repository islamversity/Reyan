package com.islamversity.home.view

import android.app.Application
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.islamversity.base.CoroutineView
import com.islamversity.base.transitionNameCompat
import com.islamversity.core.mvi.MviPresenter
import com.islamversity.daggercore.CoreComponent
import com.islamversity.daggercore.coreComponent
import com.islamversity.daggercore.lifecycleComponent
import com.islamversity.daggercore.navigator.DaggerDefaultNavigationComponent
import com.islamversity.home.HomeIntent
import com.islamversity.home.HomeState
import com.islamversity.home.databinding.ViewHomeBinding
import com.islamversity.home.di.DaggerHomeComponent
import kotlinx.coroutines.flow.*
import ru.ldralighieri.corbind.view.clicks
import javax.inject.Inject

class HomeView : CoroutineView<ViewHomeBinding, HomeState, HomeIntent> {

    @Inject
    override lateinit var presenter: MviPresenter<HomeIntent, HomeState>

    constructor() : super()

    constructor(app: Application) : this(core = app.coreComponent())

    constructor(core: CoreComponent) : super() {
        coreComponent = core
    }

    override fun injectDependencies(core: CoreComponent) {
        DaggerHomeComponent
            .builder()
            .coreComponent(core)
            .lifecycleComponent(activity!!.lifecycleComponent)
            .navComponent(DaggerDefaultNavigationComponent.factory().create(core, router))
            .build()
            .inject(this)
    }

    override fun bindView(
        inflater: LayoutInflater,
        container: ViewGroup
    ): ViewHomeBinding = ViewHomeBinding.inflate(inflater, container, false)

    override fun beforeBindingView(binding: ViewHomeBinding) {
        initRv(binding)
        binding.ablHome.outlineProvider = null
        binding.txtSearchName.transitionNameCompat = "search_name"
        binding.toolbarHome.transitionNameCompat = "search_back"
    }

    private fun initRv(binding: ViewHomeBinding) {
        binding.rvHome.layoutManager = GridLayoutManager(binding.root.context, 3)
    }

    override fun render(state: HomeState) {
        renderError(state.base)
        renderLoading(state.base)

        binding.rvHome.withModelsAsync {
        }
    }

    override fun intents(): Flow<HomeIntent> =
        merge(
            flowOf(HomeIntent.Initial),
            binding.imgSearch.clicks()
                .map {
                    HomeIntent.SearchClicks(
                        binding.toolbarHome.transitionNameCompat ?: "",
                        binding.txtSearchName.transitionNameCompat ?: ""
                    )
                }
        )
}
