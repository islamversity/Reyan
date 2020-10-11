package com.islamversity.search.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.islamversity.base.CoroutineView
import com.islamversity.base.hideKeyboard
import com.islamversity.base.showKeyboard
import com.islamversity.base.transitionNameCompat
import com.islamversity.core.Mapper
import com.islamversity.core.mvi.MviPresenter
import com.islamversity.core.throttleFirst
import com.islamversity.daggercore.CoreComponent
import com.islamversity.navigation.model.SearchLocalModel
import com.islamversity.navigation.model.fromData
import com.islamversity.search.SearchIntent
import com.islamversity.search.SearchState
import com.islamversity.search.databinding.ViewSearchBinding
import com.islamversity.search.di.DaggerSearchComponent
import com.islamversity.search.model.SurahRowActionModel
import com.islamversity.search.model.SurahUIModel
import com.islamversity.view_component.SurahItemModel
import com.islamversity.view_component.surahItem
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import ru.ldralighieri.corbind.widget.textChangeEvents
import javax.inject.Inject
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class SearchView(
    bundle: Bundle? = null
) : CoroutineView<ViewSearchBinding, SearchState, SearchIntent>(bundle) {

    private val itemClickChannel = Channel<SurahRowActionModel>()

    private val searchLocal: SearchLocalModel? =
        bundle
            ?.getString(SearchLocalModel.EXTRA_SEARCH)
            ?.let {
                SearchLocalModel.fromData(it)
            }

    @Inject
    override lateinit var presenter: MviPresenter<SearchIntent, SearchState>

    @Inject
    lateinit var surahMapper : Mapper<SurahUIModel, SurahItemModel>

    override fun bindView(inflater: LayoutInflater, container: ViewGroup): ViewSearchBinding =
        ViewSearchBinding.inflate(inflater, container, false)

    override fun injectDependencies(core: CoreComponent) {
        DaggerSearchComponent
            .builder()
            .bindRouter(router)
            .coreComponent(core)
            .build()
            .inject(this)
    }

    override fun beforeBindingView(binding: ViewSearchBinding) {
        searchLocal?.apply {
            binding.viewSearchBar.transitionNameCompat = backTransitionName
            binding.edtSearch.transitionNameCompat = textTransitionName
        }

        binding.imgBack.setOnClickListener {
            it.hideKeyboard()
            router.handleBack()
        }
        binding.edtSearch.requestFocus()
        binding.root.showKeyboard()

        val layoutManager = GridLayoutManager(binding.root.context, 3)
        binding.listSearch.layoutManager = layoutManager
    }

    override fun onDestroyView(view: View) {
        binding.listSearch.adapter = null
        super.onDestroyView(view)
    }

    override fun intents(): Flow<SearchIntent> =
        listOf(
            searchQueryIntents(),
            itemClickIntent()
        ).merge()


    private fun searchQueryIntents() =
        binding.edtSearch.textChangeEvents()
            .filter { it.text.toString().length > 1 }
            .debounce(500.0.toDuration(DurationUnit.MILLISECONDS))
            .map {
                it.text.toString()
            }
            .map {
                SearchIntent.Search(it)
            }


    private fun itemClickIntent() = itemClickChannel
        .receiveAsFlow()
        .throttleFirst(300.toDuration(DurationUnit.MILLISECONDS))
        .map {
            SearchIntent.ItemClick(it)
        }

    override fun render(state: SearchState) {
        renderLoading(state.base)
        renderError(state.base)

        renderList(state)
    }

    private fun renderList(state: SearchState) {
        binding.listSearch.withModelsAsync {
            state.items.forEach { model ->
                surahItem {
                    id(model.id.id)
                    uiItem(surahMapper.map(model))
                    listener {
                        itemClickChannel.offer(SurahRowActionModel(model))
                    }
                }
            }
        }
    }
}