package com.github.mhelmi.localweather.data.weather.source.remote.model

import com.google.gson.annotations.SerializedName

data class GetWeatherForecastResponse(
  @SerializedName("location")
  val city: CityRemote?,
  @SerializedName("current")
  val currentWeatherRemote: CurrentWeatherRemote?,
  @SerializedName("forecast")
  val forecast: ForecastRemote?
)

data class ForecastRemote(
  @SerializedName("forecastday")
  val forecastDaysList: List<ForecastDayRemote>?
)

data class WeatherErrorRemote(
  @SerializedName("error")
  val error: ErrorRemote?
)

data class ErrorRemote(
  @SerializedName("code")
  val code: Int?,
  @SerializedName("message")
  val message: String?
)