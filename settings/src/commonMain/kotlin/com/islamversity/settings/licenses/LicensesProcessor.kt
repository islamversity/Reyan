package com.islamversity.settings.licenses

import com.islamversity.core.FlowBlock
import com.islamversity.core.Mapper
import com.islamversity.core.listMap
import com.islamversity.core.mvi.BaseProcessor
import com.islamversity.core.ofType
import com.islamversity.domain.repo.LicensesRepo
import com.islamversity.domain.model.LicensesRepoModel
import com.islamversity.settings.models.LicensesUIModel
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class LicensesProcessor(
    uiMapper: Mapper<LicensesRepoModel, LicensesUIModel>,
    licensesRepo: LicensesRepo
) : BaseProcessor<LicensesIntent, LicensesResult>() {
    override fun transformers(): List<FlowBlock<LicensesIntent, LicensesResult>> = listOf(getLicenses)


val getLicenses: FlowBlock<LicensesIntent, LicensesResult> = {

    ofType<LicensesIntent.Initial>().flatMapLatest {
        licensesRepo.getAllLicenses()
    }.map {
        LicensesResult.LicensesList(
            uiMapper.listMap(it)
        )
    }
}
}
