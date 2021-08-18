package com.islamversity.base.ext

import android.view.animation.LinearInterpolator
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

fun RecyclerView.setHidable(
    fab: FloatingActionButton,
    tvSurahName: TextView,
) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            if (isTop()) {
                tvSurahName.animate().apply {
                    interpolator = LinearInterpolator()
                    duration = 200
                    alpha(0f)
                    start()
                }
            } else if (tvSurahName.alpha==0F) {
                tvSurahName.animate().apply {
                    interpolator = LinearInterpolator()
                    duration = 200
                    alpha(1f)
                    start()
                }
            }

            if (isTop() || dy > 0 && fab.isShown) {
                fab.hide()
            } else if (dy < 0 && !fab.isShown) {
                fab.show()
            }
        }
    })
}
fun RecyclerView.isTop() = (layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition() == 0