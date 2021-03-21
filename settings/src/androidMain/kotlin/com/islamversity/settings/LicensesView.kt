package com.islamversity.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import com.islamversity.base.CoroutineView
import com.islamversity.core.mvi.MviPresenter
import com.islamversity.daggercore.CoreComponent
import com.islamversity.settings.databinding.ViewLicensesBinding
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class LicensesView : CoroutineView<ViewLicensesBinding,LicensesState , LicensesIntent>()
{
    @Inject
    override lateinit var presenter: MviPresenter<LicensesIntent, LicensesState>

    override fun injectDependencies(core: CoreComponent) {
        TODO("Not yet implemented")
    }

    override fun render(state: LicensesState) {
        TODO("Not yet implemented")
    }

    override fun intents(): Flow<LicensesIntent> {
        TODO("Not yet implemented")
    }

    override fun bindView(inflater: LayoutInflater, container: ViewGroup): ViewLicensesBinding {
        TODO("Not yet implemented")
    }


}