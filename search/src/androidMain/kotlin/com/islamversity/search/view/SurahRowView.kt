package com.islamversity.search.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.islamversity.search.databinding.RowSuraBinding
import com.islamversity.search.model.SurahRowActionModel
import com.islamversity.search.model.SurahUIModel

@ModelView(
    autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT,
    saveViewState = true
)
class SurahRowView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : LinearLayout(context, attributeSet) {

    private val binding = RowSuraBinding.inflate(LayoutInflater.from(context), this, true)


    lateinit var uiItem: SurahUIModel

    @ModelProp
    fun uiItem(item: SurahUIModel) {
        uiItem = item
        binding.txtviewSurahName.text = uiItem.name
        binding.txtviewSurahOrder.text = uiItem.order.toString()
    }

    @CallbackProp
    fun listener(listener: ((rowAction: SurahRowActionModel) -> Unit)?) {
        if (listener == null) {
            binding.root.setOnClickListener(null)
        } else {
            binding.root.setOnClickListener {
                listener(SurahRowActionModel(uiItem))
            }
        }
    }
}

