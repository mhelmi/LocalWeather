package com.github.mhelmi.localweather.common

import com.google.gson.Gson

fun Any?.toGson(): String = Gson().toJson(this)

fun <T> String?.toObject(classOfT: Class<T>): T = Gson().fromJson(this, classOfT)