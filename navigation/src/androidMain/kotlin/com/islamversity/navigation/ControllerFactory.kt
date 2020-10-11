package com.islamversity.navigation

import android.app.Application
import android.os.Bundle
import com.bluelinelabs.conductor.Controller
import java.lang.reflect.Constructor

object ControllerFactory {

    @JvmStatic
    fun createController(controller: Screens, app: Application? = null): Controller {
        val constructors = Class.forName(controller.name)
            .constructors

        return app
            ?.run {
                controller.createWithContext(constructors, this)
            }
            ?: controller.createWithBundle(constructors)
            ?: createWithEmptyConstructor(constructors)
    }

    private fun Screens.createWithContext(
        constructors: Array<Constructor<*>>,
        app: Application
    ): Controller? =
        constructors
            .find { constructor ->
                constructor
                    .parameterTypes
                    .find { param ->
                        param.isAssignableFrom(Application::class.java)
                    } != null
            }?.let { constructor ->
                val needsBundle = constructor.parameterTypes.find { param ->
                    param.isAssignableFrom(Bundle::class.java)
                } != null

                val extra = Bundle().apply {
                    if (extras != null) {
                        putString(extras.first, extras.second)
                    }
                }

                if (needsBundle) {
                    constructor.newInstance(app, extra)
                } else {
                    constructor.newInstance(app)
                } as Controller
            }

    private fun Screens.createWithBundle(constructors: Array<Constructor<*>>): Controller? {
        if (extras == null) {
            return null
        }


        val extra = Bundle().apply {
            putString(extras.first, extras.second)
        }

        return try {
            constructors
                .first()
                .newInstance(extra) as Controller
        } catch (e: IllegalArgumentException) {
            null
        }
    }

    private fun createWithEmptyConstructor(constructors: Array<Constructor<*>>): Controller =
        constructors
            .first()
            .newInstance() as Controller
}