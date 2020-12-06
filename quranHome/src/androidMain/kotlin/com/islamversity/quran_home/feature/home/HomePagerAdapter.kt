package com.islamversity.quran_home.feature.home

import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.viewpager.RouterPagerAdapter
import com.islamversity.quran_home.R
import com.islamversity.quran_home.feature.juz.JuzListView
import com.islamversity.quran_home.feature.surah.SurahListView

class HomePagerAdapter(private val controller: Controller) : RouterPagerAdapter(controller) {

    override fun configureRouter(router: Router, position: Int) {
        getRouter(position)
        if (!router.hasRootController()) {
            when (position) {
                0 -> router.setRoot(RouterTransaction.with(SurahListView()))
                1 -> router.setRoot(RouterTransaction.with(JuzListView()))
            }
        }
    }

    override fun getCount() = 2

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> controller.activity?.getString(R.string.home_tab_surahs)
            1 -> controller.activity?.getString(R.string.home_tab_parts)
            else -> error("we have only two items but position is=$position")
        }
    }
}