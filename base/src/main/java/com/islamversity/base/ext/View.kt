package com.islamversity.base.ext

import android.view.View

fun View.hide(){
    visibility = View.INVISIBLE
}
fun View.show(){
    visibility = View.VISIBLE
}
fun View.gone(){
    visibility = View.GONE
}
fun View.getString(id : Int) = context.getString(id)