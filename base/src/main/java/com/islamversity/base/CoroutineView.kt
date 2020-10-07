package com.islamversity.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.islamversity.core.Logger
import com.islamversity.core.Severity
import com.islamversity.core.mvi.BaseState
import com.islamversity.core.mvi.MviIntent
import com.islamversity.core.mvi.MviPresenter
import com.islamversity.core.mvi.MviViewState
import com.islamversity.daggercore.CoreComponent
import com.islamversity.daggercore.coreComponent
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Suppress("UNUSED_PARAMETER")
abstract class CoroutineView<
        V : ViewBinding,
        S : MviViewState,
        I : MviIntent
        > @JvmOverloads constructor(
    bundle: Bundle? = null
) : ViewBindingController<V>(bundle), CoroutineScope by MainScope(){

    lateinit var coreComponent: CoreComponent

    abstract var presenter: MviPresenter<I, S>

    var loadingView: View? = null
    var errorSnack: Snackbar? = null

    private val inject by lazy {
        prepareDependencies()
    }

    override fun onContextAvailable(context: Context) {
        super.onContextAvailable(context)
        inject
    }

    @CallSuper
    final override fun onViewBound(binding: V) {
        beforeBindingView(binding)
        bind()
        createLoading()
        createErrorSnack()
    }

    open fun beforeBindingView(binding : V){
    }

    override fun onDestroyView(view: View) {
        coroutineContext.cancelChildren()
        loadingView = null
        errorSnack = null
        super.onDestroyView(view)
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
        presenter.close()
    }

    private fun prepareDependencies() {
        if (!::coreComponent.isInitialized) {
            coreComponent = applicationContext!!.coreComponent()
        }
        injectDependencies(coreComponent)
    }

    private fun bind() {
        intents()
            .newIntents()

        presenter
            .states()
            .catch {
                Logger.log(Severity.Error, "PublishingState", it, "presenter state flow exception caught: ${it.localizedMessage}")
            }
            .onEach {
                render(it)
            }
            .launchIn(this)
    }

    @Suppress("MemberVisibilityCanBePrivate")
    protected fun Flow<I>.newIntents() =
        catch {
            Logger.log(Severity.Error, "ReceivingIntents", it, "receiving intents faced exception, caught: ${it.localizedMessage}")
        }.onEach {
            presenter.processIntents(it)
        }.launchIn(this@CoroutineView)

    protected fun renderError(baseState: BaseState) {
//        view?.run {
//            if (baseState.error.showSnackBar) {
//                val error = baseState.error.getErrorString(context)
//                if (errorSnack == null) {
//                    createErrorSnack()
//                }
//                errorSnack?.setText(error)
//                if (errorSnack?.isShown != true) {
//                    errorSnack?.show()
//                } else {
//                    empty
//                }
//            } else {
//                errorSnack?.dismiss()
//            }
//        }
    }

    protected fun renderLoading(baseState: BaseState) {
//        if (baseState.showLoading) {
//            if (loadingView == null) {
//                createLoading()
//            }
//            loadingView?.visible(true)
//        } else {
//            loadingView?.visible(false)
//        }
    }

    private fun createLoading() {
        view?.also {
            val parent: ViewGroup? =
                if (it is ViewGroup) {
                    it
                } else {
                    if (it.parent is ViewGroup) {
                        it.parent as ViewGroup
                    } else {
                        null
                    }
                }

            if (parent != null) {
                loadingView = LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.view_loading, parent, false)
                parent.addView(loadingView)
                loadingView!! visible false
            }
        }
    }

    private fun createErrorSnack() {
//        view?.run {
//            errorSnack = Snackbar.make(this, "", Snackbar.LENGTH_LONG)
//        }
    }

    protected abstract fun injectDependencies(core: CoreComponent)

    protected abstract fun render(state: S)

    protected abstract fun intents(): Flow<I>
}