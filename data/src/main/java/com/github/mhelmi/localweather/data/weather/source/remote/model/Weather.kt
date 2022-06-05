package com.github.mhelmi.localweather.data.weather.source.remote.model

import com.google.gson.annotations.SerializedName

data class CurrentWeatherRemote(
  @SerializedName("temp_c")
  val tempC: Double?,
  @SerializedName("temp_f")
  val tempF: Double?,
  @SerializedName("condition")
  val condition: ConditionRemote?,
  @SerializedName("wind_mph")
  val windMph: Double?,
  @SerializedName("wind_kph")
  val windKph: Double?,
  @SerializedName("wind_degree")
  val windDegree: Double?,
  @SerializedName("wind_dir")
  val windDir: String?,
  @SerializedName("humidity")
  val humidity: Double?,
  @SerializedName("feelslike_c")
  val feelsLikeC: Double?,
  @SerializedName("feelslike_f")
  val feelsLikeF: Double?
)

data class ForecastDayRemote(
  @SerializedName("date")
  val date: String?,
  @SerializedName("date_epoch")
  val dateEpoch: Int?,
  @SerializedName("day")
  val dayWeather: DayWeatherRemote?
)

data class DayWeatherRemote(
  @SerializedName("maxtemp_c")
  val maxTempC: Double?,
  @SerializedName("maxtemp_f")
  val maxTempF: Double?,
  @SerializedName("mintemp_c")
  val minTempC: Double?,
  @SerializedName("mintemp_f")
  val minTempF: Double?,
  @SerializedName("avgtemp_c")
  val avgTempC: Double?,
  @SerializedName("avgtemp_f")
  val avgTempF: Double?,
  @SerializedName("maxwind_mph")
  val maxWindMph: Double?,
  @SerializedName("maxwind_kph")
  val maxWindKph: Double?,
  @SerializedName("avghumidity")
  val avgHumidity: Double?,
  @SerializedName("condition")
  val condition: ConditionRemote?
)

data class ConditionRemote(
  @SerializedName("text")
  val text: String?,
  @SerializedName("icon")
  val icon: String?
)
