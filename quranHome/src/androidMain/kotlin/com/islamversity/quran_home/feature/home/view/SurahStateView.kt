package com.islamversity.quran_home.feature.home.view

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.islamversity.base.visible
import com.islamversity.quran_home.R
import com.islamversity.quran_home.feature.home.SavedSurahState

class SurahStateView : CardView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var title: TextView? = null

    init {
        inflate(context, R.layout.sura_state_view, this)
        title = findViewById(R.id.title)
    }


    fun show(state: SavedSurahState) {
        title?.text = state.surahName
        visible(true)
    }

    fun hide() {
        visible(false)
    }

}