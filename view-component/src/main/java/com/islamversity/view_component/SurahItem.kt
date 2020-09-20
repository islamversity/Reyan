package com.islamversity.view_component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.StringRes
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
    defStyleAttr: Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr) {

    private val binding = RowSurahBinding.inflate(LayoutInflater.from(context), this, true)

    private lateinit var uiItem: SurahItemModel

    @ModelProp
    fun uiItem(item: SurahItemModel) {
        uiItem = item
        binding.txtCalligraphyName.text = uiItem.mainName
        binding.txtVersesCount.text = binding.root.context.getString(R.string.ayaCount, uiItem.ayaCount)
        binding.txtName.text = uiItem.arabicName
        binding.itemOrder.txtOrder.text = uiItem.order
        binding.txtRevealed.text = binding.root.context.getString(item.revealType.id)
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
    val arabicName: String,
    val mainName: String,
    val order: String,
    val revealType: RevealedType,
    val ayaCount: String,
){
    enum class RevealedType(@StringRes val id : Int, val rawName : String) {
        MECCAN(R.string.meccan, "meccan"),
        MEDINAN(R.string.medinan, "medinan"),
        ;

        companion object{
            operator fun invoke(rawName : String) : RevealedType =
                values().find { it.rawName == rawName }!!
        }
    }
}

