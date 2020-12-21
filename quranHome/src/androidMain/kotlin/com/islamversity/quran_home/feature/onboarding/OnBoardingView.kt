package com.islamversity.quran_home.feature.onboarding

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.islamversity.base.CoroutineView
import com.islamversity.core.mvi.MviPresenter
import com.islamversity.daggercore.CoreComponent
import com.islamversity.daggercore.helpers.languageConfigure
import com.islamversity.quran_home.R
import com.islamversity.quran_home.databinding.OnboardingViewBinding
import com.islamversity.quran_home.feature.onboarding.di.DaggerOnBoardingComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import ru.ldralighieri.corbind.view.clicks
import java.text.NumberFormat
import javax.inject.Inject

class OnBoardingView : CoroutineView<OnboardingViewBinding, OnBoardingState, OnBoardingIntent>() {

    @Inject
    override lateinit var presenter: MviPresenter<OnBoardingIntent, OnBoardingState>

    private lateinit var numberFormatter: NumberFormat

    override fun injectDependencies(core: CoreComponent) {
        DaggerOnBoardingComponent.factory().create(router, core).inject(this)
    }

    override fun bindView(inflater: LayoutInflater, container: ViewGroup): OnboardingViewBinding =
        OnboardingViewBinding.inflate(inflater, container, false)

    override fun beforeBindingView(binding: OnboardingViewBinding) {
        super.beforeBindingView(binding)
        numberFormatter = NumberFormat.getInstance(
            languageConfigure
                .getCurrentLocale()
                .locale
        )

        binding.appLanguageSelector.layoutManager =
            GridLayoutManager(binding.root.context, 3, GridLayoutManager.HORIZONTAL, false)
        binding.appLanguageSelector.withModels {
            languageConfigure.getSupportedLanguages().map { it.localeName }
                .forEachIndexed { index, it ->
                    textSelectionView {
                        id(index)
                        text(it)
                        val background: Drawable? =
                            if (languageConfigure.getCurrentLocale().localeName == it) {
                                ContextCompat.getDrawable(
                                    binding.root.context,
                                    R.drawable.ic_bg_selection_capsule
                                )
                            } else {
                                null
                            }
                        background(background)
                        clicks { selectedLocale ->
                            languageConfigure.setNewLanguage(
                                languageConfigure
                                    .getSupportedLanguages()
                                    .find { it.localeName == selectedLocale }!!
                                    .locale
                            )
                        }
                    }
                }
        }
    }

    override fun render(state: OnBoardingState) {
        renderError(state.base)
        renderLoading(state.base)

        binding.pbLoadingBar.progress = state.loadingPercent
        binding.progressPercentTextView.text = buildString {
            append(numberFormatter.format (state.loadingPercent))
            append(" %")
        }

        binding.btnContinue.isEnabled = state.showContinueBtn
        if (state.showContinueBtn) {
            binding.progressTitleTextView.text = applicationContext!!.getString(R.string.completed)
        }
    }

    override fun intents(): Flow<OnBoardingIntent> =
        listOf(
            flowOf(OnBoardingIntent.Initial),
            binding.btnContinue.clicks().map { OnBoardingIntent.Continue },
        ).merge()
}
