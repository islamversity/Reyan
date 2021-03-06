package com.islamversity.view_component.ext

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.epoxy.EpoxyRecyclerView

fun EpoxyRecyclerView.firstVisiblePosition() : Int {
    return when (val manager = layoutManager) {
        is LinearLayoutManager -> {
            manager.findFirstCompletelyVisibleItemPosition()
        }
        is GridLayoutManager -> {
            manager.findFirstCompletelyVisibleItemPosition()
        }
        else -> error("layoutManager $manager is not supported to find first visible position")
    }
}