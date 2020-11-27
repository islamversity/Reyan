package com.islamversity.surah.model

interface UIItem {
    val rowId: String
}

data class AyaUIModel(
    override val rowId: String,
    val content: String,
    val translation1: String? = null,
    val translation2: String? = null,
    val order: Long,
    val fontSize: Int,
    val translationFontSize: Int = 0,
    val hizb: Long?,
    val juz: Long?,
    val sajdah: SajdahTypeUIModel,
) : UIItem

sealed class SajdahTypeUIModel {
    sealed class VISIBLE : SajdahTypeUIModel() {

        object RECOMMENDED : VISIBLE()
        object OBLIGATORY : VISIBLE()
    }

    object NONE : SajdahTypeUIModel()
}

data class SurahHeaderUIModel(
    override val rowId: String,
    val number: String,
    val name: String,
    val nameTranslated: String,
    val origin: String,
    val verses: Int,
    val fontSize: Int,
    val showBismillah: Boolean,
) : UIItem