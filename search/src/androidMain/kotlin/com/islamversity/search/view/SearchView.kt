package com.islamversity.search.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.islamversity.base.*
import com.islamversity.core.mvi.MviPresenter
import com.islamversity.daggercore.CoreComponent
import com.islamversity.navigation.fromByteArray
import com.islamversity.navigation.model.SearchLocalModel
import com.islamversity.search.SearchIntent
import com.islamversity.search.SearchState
import com.islamversity.search.databinding.ViewSearchBinding
import com.islamversity.search.di.DaggerSearchComponent
import kotlinx.coroutines.flow.*
import ru.ldralighieri.corbind.widget.textChangeEvents
import javax.inject.Inject
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class SearchView(
    bundle: Bundle
) : CoroutineView<ViewSearchBinding, SearchState, SearchIntent>(bundle) {

    private val searchLocal: SearchLocalModel? =
        bundle
            .getByteArray(SearchLocalModel.EXTRA_SEARCH)
            ?.let {
                SearchLocalModel.fromByteArray(it)
            }

    @Inject
    override lateinit var presenter: MviPresenter<SearchIntent, SearchState>

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
        searchLocal?.run {
            binding.vSearchBack.transitionNameCompat = backTransitionName
            binding.edtSearch.transitionNameCompat = textTransitionName
        }

        binding.imgBack.setOnClickListener {
            it.hideKeyboard()
            router.handleBack()
        }
        binding.edtSearch.requestFocus()
        binding.root.showKeyboard()

        val layoutManager = GridLayoutManager(binding.root.context, 3)
        binding.searchList.layoutManager = layoutManager
    }

    override fun onDestroyView(view: View) {
        binding.searchList.adapter = null
        super.onDestroyView(view)
    }

    override fun render(state: SearchState) {
        renderLoading(state.base)
        renderError(state.base)

        renderList(state)
    }

    override fun intents(): Flow<SearchIntent> =
        listOf(
            searchQueryIntents(),
            nextPageIntents()
        ).merge()

    private fun searchQueryIntents() =
        binding.edtSearch.textChangeEvents()
            .filter { it.text.toString().length > 1 }
            .debounce(1.0.toDuration(DurationUnit.SECONDS))
            .map {
                it.text.toString()
            }
            .map {
                SearchIntent.Search(it)
            }

    private fun nextPageIntents() =
        binding.searchList.pages()
            .combine(binding.edtSearch.textChangeEvents()
                .filter { it.text.toString().length > 1 }
            ) { first, second ->
                first to second
            }
            .map {
                SearchIntent.NextPage(
                    it.second.text.toString(),
                    it.first.totalItemsCount,
                    it.first.page
                )
            }

    private fun renderList(state: SearchState) {
        binding.searchList.withModelsAsync {
        }
    }
}