package com.islamversity.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import com.islamversity.base.CoroutineView
import com.islamversity.core.mvi.MviPresenter
import com.islamversity.daggercore.CoreComponent
import com.islamversity.settings.databinding.ViewFosslicenseBinding
import kotlinx.coroutines.flow.Flow


class FossLicenseView : CoroutineView<ViewFosslicenseBinding,LicensesState , LicensesIntent>()

{
    override var presenter: MviPresenter<LicensesIntent, LicensesState>
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun injectDependencies(core: CoreComponent) {
        TODO("Not yet implemented")
    }

    override fun render(state: LicensesState) {
        TODO("Not yet implemented")
    }

    override fun intents(): Flow<LicensesIntent> {
        TODO("Not yet implemented")
    }

    override fun bindView(inflater: LayoutInflater, container: ViewGroup): ViewFosslicenseBinding {
        TODO("Not yet implemented")
    }


}