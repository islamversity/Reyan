package com.islamversity.view_component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.islamversity.view_component.databinding.RowLicenseBinding

@ModelView(
    autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT, saveViewState = true
)
class LicenseItem @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attributeSet, defStyleAttr) {

    private val binding = RowLicenseBinding.inflate(LayoutInflater.from(context), this, true)


    @ModelProp
    fun uiItem(item: LicenseItemModel) {
        binding.txtLicenseRow.text = item.name
        binding.txtLicenseURLRow.text = item.address
    }
}

data class LicenseItemModel (val id: Int, val name: String, val address: String)