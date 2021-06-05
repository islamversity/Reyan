package com.islamversity.settings.models

import com.islamversity.core.Mapper
import com.islamversity.domain.model.LicensesRepoModel

class LicenseDomainUIMapper : Mapper<LicensesRepoModel, LicensesUIModel> {
    override fun map(item: LicensesRepoModel) = LicensesUIModel (
        item.id,
        item.name,
        item.address
    )
}