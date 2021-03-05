package com.islamversity.settings

import com.islamversity.core.FlowBlock
import com.islamversity.core.Mapper
import com.islamversity.core.mvi.BaseProcessor
import com.islamversity.domain.model.LicenseRepoModel
import com.islamversity.settings.models.LicenseUIModel

class FossLicenseProcessor(
uiMapper: Mapper<LicenseRepoModel, LicenseUIModel>
) : BaseProcessor<SettingsIntent, SettingsResult>() {
    override fun transformers(): List<FlowBlock<SettingsIntent, SettingsResult>> {
        TODO("Not yet implemented")
    }


}