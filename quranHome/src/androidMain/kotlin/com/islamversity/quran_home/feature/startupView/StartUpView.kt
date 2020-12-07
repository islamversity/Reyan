package com.islamversity.quran_home.feature.startupView

import android.app.Application
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.RouterTransaction
import com.islamversity.core.Logger
import com.islamversity.daggercore.coreComponent
import com.islamversity.navigation.ControllerFactory
import com.islamversity.navigation.Screens
import kotlinx.coroutines.*

class StartUpView : Controller(), CoroutineScope by MainScope() {

    override fun onContextAvailable(context: Context) {
        super.onContextAvailable(context)
        checkDatabase(context.applicationContext!! as Application)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedViewState: Bundle?): View =
        View(container.context).also {
            it.background = ColorDrawable(Color.TRANSPARENT)
        }

    private fun checkDatabase(app : Application) {
        launch {
            val needs = withContext(Dispatchers.IO){
                //maybe sharedPrefs are faster
                //for now we can stick to database
                app.coreComponent().databaseFillerUsecase().needsFilling()
            }
            Logger.log(tag = "StartupView") { "database needs filling= $needs" }
            if (isActive) {
                router.setRoot(RouterTransaction.with(ControllerFactory.createController(Screens.Home, app)))
            }
        }
    }

    override fun onDestroy() {
        cancel()
        super.onDestroy()
    }
}