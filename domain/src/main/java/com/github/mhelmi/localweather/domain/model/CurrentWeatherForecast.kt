package com.github.mhelmi.localweather.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CurrentWeatherForecast(
  val city: City,
  val currentWeather: CurrentWeather,
  val forecastDaysList: List<ForecastDay>
) : Parcelable

@Parcelize
data class CurrentWeather(
  val temp: Double,
  val condition: Condition,
  val windKph: Double,
  val windDegree: Double,
  val windDir: String,
  val humidity: Double,
  val feelsLike: Double,
) : Parcelable

@Parcelize
data class ForecastDay(
  val date: String?,
  val timestamp: Int?,
  val dayWeather: DayWeather
) : Parcelable

@Parcelize
data class DayWeather(
  val avgTemp: Double,
  val maxWindKph: Double,
  val avgHumidity: Double,
  val condition: Condition?
) : Parcelable


@Parcelize
data class Condition(
  val text: String,
  val icon: String
) : Parcelable