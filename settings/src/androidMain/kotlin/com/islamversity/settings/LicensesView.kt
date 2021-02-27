package com.islamversity.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import com.islamversity.core.Mapper
import com.islamversity.base.CoroutineView
import com.islamversity.core.mvi.MviPresenter
import com.islamversity.daggercore.CoreComponent
import com.islamversity.settings.databinding.ViewLicensesBinding
import com.islamversity.settings.di.DaggerLicensesComponent
import com.islamversity.view_component.*
import com.islamversity.settings.licenses.LicensesIntent
import com.islamversity.settings.licenses.LicensesState
import com.islamversity.settings.models.LicensesUIModel
import com.islamversity.view_component.licenseItem
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.merge
import javax.inject.Inject
import android.content.Intent

class LicensesView : CoroutineView<ViewLicensesBinding, LicensesState, LicensesIntent>() {
    @Inject
    override lateinit var presenter: MviPresenter<LicensesIntent, LicensesState>

    @Inject
    lateinit var licenseMapper: Mapper<LicensesUIModel, LicenseItemModel>

    val urlOpen = Intent(android.content.Intent.ACTION_VIEW)
    private var licensesList: List<LicensesUIModel> = emptyList()

    override fun injectDependencies(core: CoreComponent) {
        DaggerLicensesComponent.factory().create(core).inject(this)
    }

    override fun render(state: LicensesState) {
        renderLoading(state.base)
        renderError(state.base)
        licensesList = state.licenses

        binding.licenseList.withModelsAsync {

            state.licenses.forEach { model ->
                licenseItem {
                    id(model.id)
                    uiItem(licenseMapper.map(model))
                    listener {
                        urlOpen.data = android.net.Uri.parse(it.address)
                        startActivity(urlOpen)
                    }
                }
            }
        }
    }

    override fun intents(): Flow<LicensesIntent> =
        listOf(flowOf(LicensesIntent.Initial))
            .merge()

    override fun bindView(inflater: LayoutInflater, container: ViewGroup): ViewLicensesBinding {
        return ViewLicensesBinding.inflate(inflater, container, false)
    }


}