package com.islamversity.quran_home.feature.home.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import com.islamversity.base.visible
import com.islamversity.core.Logger
import com.islamversity.quran_home.databinding.SurahStateViewBinding
import com.islamversity.quran_home.feature.home.BookmarkState
import java.text.NumberFormat
import java.util.*

class SurahStateView : CardView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    private val binding = SurahStateViewBinding.inflate(LayoutInflater.from(context), this, true)
    private val numberFormatter = NumberFormat.getNumberInstance(Locale.getDefault())

    private var state: BookmarkState? = null

    fun render(state: BookmarkState?) {
        Logger.log {
            "received bookmarkState= $state"
        }
        if (state != null) {
            show(state)
        } else {
            hide()
        }
    }

    @SuppressLint("SetTextI18n")
    fun show(state: BookmarkState) {
        this.state = state
        visible(true)
        binding.getRoot() visible true
        binding.title.text = "${state.surahName} - ${numberFormatter.format(state.ayaOrder)}"
    }

    fun hide() {
        binding.getRoot() visible false
        state = null
        visible(false)
    }

    fun stateClickListener(listener: (BookmarkState) -> Unit) {
        setOnClickListener {
            listener(state!!)
        }
    }
}