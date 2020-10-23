package com.islamversity.surah.view.utils

import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.DiffResult
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.OnModelBuildFinishedListener

class BuildFinishedScroller(
    private val scrollTo: Int,
    private val controller: EpoxyController,
    private val view: RecyclerView,
) : OnModelBuildFinishedListener {

    override fun onModelBuildFinished(result: DiffResult) {
        view.scrollToPosition(scrollTo)
        controller.removeModelBuildListener(this)
    }

}