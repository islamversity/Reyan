package com.islamversity.settings

import com.islamversity.core.Mapper
import com.islamversity.view_component.*
import com.islamversity.settings.models.*
class LicensesUIItemMapper : Mapper<LicensesUIModel, LicenseItemModel> {
    override fun map(item: LicensesUIModel): LicenseItemModel =
        LicenseItemModel(
            item.id,
            item.name,
            item.address
        )
}
