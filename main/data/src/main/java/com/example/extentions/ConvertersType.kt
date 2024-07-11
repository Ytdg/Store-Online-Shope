package com.example.extentions

import android.util.Log

fun List<String>.toListUriImages(): List<String> {
    val list = mutableListOf<String>()
    forEach {
        list.add(it.replace("[", "").replace("]", "").replace("\"", ""))
    }
    Log.d("(#490", list.toString())
    return list
}