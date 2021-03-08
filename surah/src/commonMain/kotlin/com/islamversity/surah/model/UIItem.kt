package com.islamversity.surah.model

import com.islamversity.domain.model.surah.RevealedType

interface UIItem {
    val rowId: String
}

data class AyaUIModel(
    override val rowId: String,
    val content: String,
    val translation1: String? = null,
    val translation2: String? = null,
    val order: Long,
    val toolbarVisible: Boolean,
    val hizb: HizbProgress?,
    val juz: Long?,
    val sajdah: SajdahTypeUIModel,
) : UIItem {

    sealed class HizbProgress(val hizb : Long){

        data class Beginning(val hizbNumber : Long) : HizbProgress(hizbNumber)

        data class Quarter(val hizbNumber : Long) : HizbProgress(hizbNumber)

        data class Half(val hizbNumber : Long) : HizbProgress(hizbNumber)

        data class ThreeFourth(val hizbNumber : Long) : HizbProgress(hizbNumber)
    }
}

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
    val origin: RevealedType,
    val verses: Int,
    val fontSize: Int,
    val showBismillah: Boolean,
) : UIItem