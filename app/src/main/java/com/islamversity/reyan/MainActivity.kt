package com.islamversity.reyan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.view.DraweeTransition
import com.islamversity.reyan.di.ActivityComponent
import com.islamversity.reyan.di.DaggerActivityComponent
import com.islamversity.daggercore.lifecycle.LifecycleComponentProvider
import com.islamversity.daggercore.lifecycle.LifecycleEvent
import com.islamversity.daggercore.lifecycle.Permissions
import com.islamversity.daggercore.lifecycle.PermissionsResult
import com.islamversity.quran_home.feature.home.QuranHomeView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.channels.Channel
import javax.inject.Inject

class MainActivity : AppCompatActivity(),
    LifecycleComponentProvider {

    @Inject
    lateinit var events: Channel<LifecycleEvent>

    private lateinit var router: Router

    private val component: ActivityComponent by lazyComponent {
        createComponent()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)

        window.sharedElementEnterTransition = DraweeTransition.createTransitionSet(
            ScalingUtils.ScaleType.CENTER_CROP, ScalingUtils.ScaleType.CENTER_CROP
        )
        window.sharedElementEnterTransition = DraweeTransition.createTransitionSet(
            ScalingUtils.ScaleType.CENTER_CROP, ScalingUtils.ScaleType.CENTER_CROP
        )

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        router = Conductor.attachRouter(this, root, savedInstanceState)
        val view = QuranHomeView()
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(view))
        }
    }

    override fun onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        events.offer(
            LifecycleEvent.Permission(
                requestCode,
                permissions.map { Permissions.fromRawValue(it) },
                grantResults.map { PermissionsResult.fromRawValue(it) }
            ))
    }

    override fun lifecycleComponent(): ActivityComponent =
        component

    private fun createComponent(): ActivityComponent =
        DaggerActivityComponent.create()
}

internal class StoreViewModel : ViewModel() {

    var component: ActivityComponent? = null

    companion object {

        fun factory() = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                if (modelClass.isAssignableFrom(StoreViewModel::class.java)) {
                    StoreViewModel() as T
                } else {
                    throw IllegalArgumentException("this factory can only provide StoreViewModel")
                }
        }
    }
}

internal fun AppCompatActivity.lazyComponent(
    createComponent: () -> ActivityComponent
): Lazy<ActivityComponent> = lazy(LazyThreadSafetyMode.NONE) {
    val store = ViewModelProvider(
        this,
        StoreViewModel.factory()
    ).get(StoreViewModel::class.java)

    if (store.component != null) {
        store.component!!
    } else {
        store.component = createComponent()
        store.component!!
    }
}
