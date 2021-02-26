package com.islamversity.view_component.ext

import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.epoxy.EpoxyRecyclerView

fun EpoxyRecyclerView.currentPosition() =
    (layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()