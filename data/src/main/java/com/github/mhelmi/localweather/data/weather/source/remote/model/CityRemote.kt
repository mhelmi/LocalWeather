package com.github.mhelmi.localweather.data.weather.source.remote.model

import com.google.gson.annotations.SerializedName

data class CityRemote(
  @SerializedName("name")
  val name: String?,
  @SerializedName("region")
  val region: String?,
  @SerializedName("country")
  val country: String?,
  @SerializedName("lat")
  val lat: Double?,
  @SerializedName("lon")
  val lon: Double?,
)