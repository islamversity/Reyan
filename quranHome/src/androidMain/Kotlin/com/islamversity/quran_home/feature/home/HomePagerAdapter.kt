package com.islamversity.quran_home.feature.home

import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.support.RouterPagerAdapter
import com.islamversity.quran_home.feature.juz.JuzListView
import com.islamversity.quran_home.feature.surah.SurahListView

/**
 * @author Ali (alirezaiyann@gmail.com)
 * @since 9/3/2020 6:52 PM.
 */
class HomePagerAdapter(controller: Controller) : RouterPagerAdapter(controller) {

    override fun configureRouter(router: Router, position: Int) {
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
            0 -> "سوره"
            1 -> "جزء"
            else -> ""
        }
    }
}