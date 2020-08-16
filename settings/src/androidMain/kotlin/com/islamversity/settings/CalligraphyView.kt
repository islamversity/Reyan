package com.islamversity.settings

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.islamversity.settings.databinding.RowCalligraphyBinding
import com.islamversity.settings.models.CalligraphyUIModel

@ModelView(
    autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT,
    saveViewState = true
)
class CalligraphyView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : LinearLayout(context, attributeSet) {

    private val binding = RowCalligraphyBinding.inflate(LayoutInflater.from(context), this, true)


    lateinit var calligraphy: CalligraphyUIModel

    @ModelProp
    fun calligraphy(calligraphy: CalligraphyUIModel) {
        this.calligraphy = calligraphy

        binding.title.text = calligraphy.name
    }


    @CallbackProp
    fun callback(call: ((CalligraphyUIModel) -> Unit)?) {
        if (call == null) {
            setOnClickListener(null)
        } else {
            setOnClickListener{
                call(calligraphy)
            }
        }
    }
}