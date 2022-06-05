package com.github.mhelmi.localweather.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class City(
  val name: String,
  val region: String,
  val country: String,
  val lat: Double,
  val lon: Double,
  var isFavorite: Boolean,
) : Parcelable