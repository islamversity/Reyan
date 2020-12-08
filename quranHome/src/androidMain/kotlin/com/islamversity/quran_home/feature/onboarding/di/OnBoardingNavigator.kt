package com.islamversity.quran_home.feature.onboarding.di

import android.app.Application
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.islamversity.navigation.AbstractControllerNavigator

class OnBoardingNavigator (
    app : Application,
    private val router : Router
): AbstractControllerNavigator(app) {
    override fun routeTo(transaction: RouterTransaction) {
        router.setRoot(transaction)
    }
}