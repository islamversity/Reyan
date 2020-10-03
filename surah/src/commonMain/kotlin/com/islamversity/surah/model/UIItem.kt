package com.islamversity.surah.model

interface UIItem{
    val rowId : String
}

data class AyaUIModel(
    override val rowId: String,
    val content : String,
    val order : Long,
    val fontSize : Int,
    val toolbarVisible : Boolean,
) : UIItem

data class SurahHeaderUIModel(
    override val rowId: String,
    val number: String,
    val name: String,
    val nameTranslated: String,
    val origin : String,
    val verses: Int,
    val fontSize: Int,
    val showBismillah : Boolean,
) : UIItem