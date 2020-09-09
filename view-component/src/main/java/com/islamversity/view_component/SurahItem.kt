package com.islamversity.view_component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.islamversity.view_component.databinding.RowSurahBinding

@ModelView(
    autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT,
    saveViewState = true
)
class SurahItem @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr : Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr) {

    private val binding = RowSurahBinding.inflate(LayoutInflater.from(context), this, true)

    lateinit var uiItem: SurahItemModel

    @ModelProp
    fun uiItem(item: SurahItemModel) {
        uiItem = item
        binding.txtName.text = uiItem.name
        binding.itemOrder.txtOrder.text = uiItem.order
        binding.txtRevealed.text = uiItem.revealType
    }

    @CallbackProp
    fun listener(listener: ((rowAction: SurahItemModel) -> Unit)?) {
        if (listener == null) {
            binding.root.setOnClickListener(null)
        } else {
            binding.root.setOnClickListener {
                listener(uiItem)
            }
        }
    }
}

data class SurahItemModel(
    val id: String,
    val name: String,
    val order: String,
    val revealType: String
)

