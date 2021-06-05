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
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class LicensesView : CoroutineView<ViewLicensesBinding, LicensesState, LicensesIntent>() {
    @Inject
    override lateinit var presenter: MviPresenter<LicensesIntent, LicensesState>
    @Inject
    lateinit var licenseMapper : Mapper<LicensesUIModel, LicenseItemModel>

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
                    }

            }
        }

    }

    override fun intents(): Flow<LicensesIntent> {
        TODO("Not yet implemented")
    }

    override fun bindView(inflater: LayoutInflater, container: ViewGroup): ViewLicensesBinding {
        return ViewLicensesBinding.inflate(inflater, container, false)
    }


}