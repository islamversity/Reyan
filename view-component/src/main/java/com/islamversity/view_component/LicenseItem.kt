package com.islamversity.view_component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.airbnb.epoxy.CallbackProp
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

    private lateinit var uiItem: LicenseItemModel

    @ModelProp
    fun uiItem(item: LicenseItemModel) {
        uiItem = item
        binding.txtLicenseRow.text = uiItem.name
        binding.txtLicenseURLRow.text = uiItem.address
    }

    @CallbackProp
    fun listener(listener: ((rowAction: LicenseItemModel) -> Unit)?) {
        if (listener == null) {
            binding.root.setOnClickListener(null)
        } else {
            binding.root.setOnClickListener {
                listener(uiItem)
            }
        }
    }
}

data class LicenseItemModel (val id: Int, val name: String, val address: String)