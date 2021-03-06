package com.islamversity.surah

import com.islamversity.core.mvi.MviIntent
import com.islamversity.navigation.model.SurahLocalModel
import com.islamversity.surah.model.AyaUIModel
import com.islamversity.surah.model.CalligraphyUIModel
import com.islamversity.surah.model.UIItem

sealed class SurahIntent : MviIntent {
    data class Initial(
            val localModel: SurahLocalModel
    ) : SurahIntent()

    sealed class ChangeSettings : SurahIntent() {
        data class QuranFontSize(
                val size: Int
        ) : ChangeSettings()

        data class AyaToolbarVisibility(
                val visible: Boolean
        ) : ChangeSettings()

        data class TranslateFontSize(
                val size: Int
        ) : ChangeSettings()

        data class NewFirstTranslation(
                val language: CalligraphyUIModel
        ) : ChangeSettings()

        data class NewSecondTranslation(
                val language: CalligraphyUIModel
        ) : ChangeSettings()

    }

    sealed class AyaActions(
            val model : AyaUIModel
    ) : SurahIntent(){
        data class Share(val aya : AyaUIModel) : AyaActions(aya)
    }

    data class Scrolled(
        val uiItems : List<UIItem>,
        val localModel: SurahLocalModel,
        val position : Int
    ) : SurahIntent()
}
