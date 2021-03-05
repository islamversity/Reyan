package com.islamversity.settings

import com.islamversity.core.FlowBlock
import com.islamversity.core.Mapper
import com.islamversity.core.mvi.BaseProcessor
import com.islamversity.domain.model.LicensesRepoModel
import com.islamversity.settings.models.LicensesUIModel

class LicensesProcessor(
uiMapper: Mapper<LicensesRepoModel, LicensesUIModel>
) : BaseProcessor<LicensesIntent, LicensesResult>() {
    override fun transformers(): List<FlowBlock<LicensesIntent, LicensesResult>> {
        TODO("Not yet implemented")
    }


}