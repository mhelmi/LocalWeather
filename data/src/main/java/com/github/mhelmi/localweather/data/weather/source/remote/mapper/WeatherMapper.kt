package com.github.mhelmi.localweather.data.weather.source.remote.mapper

import com.github.mhelmi.localweather.data.weather.source.local.mapper.toCity
import com.github.mhelmi.localweather.data.weather.source.local.model.*
import com.github.mhelmi.localweather.data.weather.source.remote.model.*
import com.github.mhelmi.localweather.domain.model.*

fun GetWeatherForecastResponse.toCurrentWeatherForecast(isCelsius: Boolean) =
  CurrentWeatherForecast(
    city = city.toCity(),
    currentWeather = currentWeatherRemote.toCurrentWeather(isCelsius),
    forecastDaysList = forecast?.forecastDaysList.toForecastDayList(isCelsius)
  )

fun CurrentWeatherRemote?.toCurrentWeather(isCelsius: Boolean) = CurrentWeather(
  temp = if (isCelsius) this?.tempC ?: 0.0 else this?.tempF ?: 0.0,
  condition = this?.condition.toCondition(),
  windKph = this?.windKph ?: 0.0,
  windDegree = this?.windDegree ?: 0.0,
  windDir = this?.windDir.orEmpty(),
  humidity = this?.humidity ?: 0.0,
  feelsLike = if (isCelsius) this?.feelsLikeC ?: 0.0 else this?.feelsLikeF ?: 0.0,
)

fun List<ForecastDayRemote>?.toForecastDayList(isCelsius: Boolean) =
  this?.map { it.toForecastDay(isCelsius) }.orEmpty()

fun ForecastDayRemote.toForecastDay(isCelsius: Boolean) = ForecastDay(
  date = date,
  timestamp = dateEpoch,
  dayWeather = dayWeather.toDayWeather(isCelsius)
)

fun DayWeatherRemote?.toDayWeather(isCelsius: Boolean) = DayWeather(
  avgTemp = if (isCelsius) this?.avgTempC ?: 0.0 else this?.avgTempF ?: 0.0,
  maxWindKph = this?.maxWindKph ?: 0.0,
  avgHumidity = this?.avgHumidity ?: 0.0,
  condition = this?.condition.toCondition()
)

fun ConditionRemote?.toCondition() = Condition(
  text = this?.text.orEmpty(),
  icon = this?.icon?.replaceFirst("//", "https://", true).orEmpty()
)

fun CurrentWeatherRemote?.toCurrentWeatherEntity(
  cityName: String,
  region: String,
  country: String
) = CurrentWeatherEntity(
  id = 0,
  cityOwnerName = cityName,
  regionOwnerName = region,
  countryOwnerName = country,
  tempC = this?.tempC ?: 0.0,
  tempF = this?.tempF ?: 0.0,
  condition = this?.condition.toConditionEntity(),
  windMph = this?.windMph ?: 0.0,
  windKph = this?.windKph ?: 0.0,
  windDegree = this?.windDegree ?: 0.0,
  windDir = this?.windDir.orEmpty(),
  humidity = this?.humidity ?: 0.0,
  feelsLikeC = this?.feelsLikeC ?: 0.0,
  feelsLikeF = this?.feelsLikeF
)

fun List<ForecastDayRemote>?.toForecastDayEntityList(
  cityName: String,
  region: String,
  country: String
) =
  this?.map { it.toForecastDayEntity(cityName, region, country) }.orEmpty()

fun ForecastDayRemote.toForecastDayEntity(
  cityName: String,
  region: String,
  country: String
) = ForecastDayEntity(
  id = 0,
  cityOwnerName = cityName,
  regionOwnerName = region,
  countryOwnerName = country,
  date = date,
  dateEpoch = dateEpoch,
  dayWeatherEntity = dayWeather.toDayWeatherEntity()
)

fun DayWeatherRemote?.toDayWeatherEntity() = DayWeatherEntity(
  maxTempC = this?.maxTempC ?: 0.0,
  maxTempF = this?.maxTempF ?: 0.0,
  minTempC = this?.minTempC ?: 0.0,
  minTempF = this?.minTempF ?: 0.0,
  avgTempC = this?.avgTempC ?: 0.0,
  avgTempF = this?.avgTempF ?: 0.0,
  maxWindMph = this?.maxWindMph ?: 0.0,
  maxWindKph = this?.maxWindKph ?: 0.0,
  avgHumidity = this?.avgHumidity ?: 0.0,
  condition = this?.condition.toConditionEntity(),
)

fun ConditionRemote?.toConditionEntity() = ConditionEntity(
  text = this?.text.orEmpty(),
  icon = this?.icon?.replaceFirst("//", "https://", true).orEmpty()
)

fun CurrentWeatherForecastEntity.toCurrentWeatherForecast(isCelsius: Boolean) =
  CurrentWeatherForecast(
    city = cityEntity.toCity(),
    currentWeather = currentWeatherEntity.toCurrentWeather(isCelsius),
    forecastDaysList = forecastDaysEntityList.toForecastDayList(isCelsius)
  )

fun CurrentWeatherEntity?.toCurrentWeather(isCelsius: Boolean) = CurrentWeather(
  temp = if (isCelsius) this?.tempC ?: 0.0 else this?.tempF ?: 0.0,
  condition = this?.condition.toCondition(),
  windKph = this?.windKph ?: 0.0,
  windDegree = this?.windDegree ?: 0.0,
  windDir = this?.windDir.orEmpty(),
  humidity = this?.humidity ?: 0.0,
  feelsLike = if (isCelsius) this?.feelsLikeC ?: 0.0 else this?.feelsLikeF ?: 0.0,
)

@JvmName("toForecastDayListForecastDayEntity")
fun List<ForecastDayEntity>?.toForecastDayList(isCelsius: Boolean) =
  this?.map { it.toForecastDay(isCelsius) }.orEmpty()

fun ForecastDayEntity.toForecastDay(isCelsius: Boolean) = ForecastDay(
  date = date,
  timestamp = dateEpoch,
  dayWeather = dayWeatherEntity.toDayWeather(isCelsius)
)

fun DayWeatherEntity?.toDayWeather(isCelsius: Boolean) = DayWeather(
  avgTemp = if (isCelsius) this?.avgTempC ?: 0.0 else this?.avgTempF ?: 0.0,
  maxWindKph = this?.maxWindKph ?: 0.0,
  avgHumidity = this?.avgHumidity ?: 0.0,
  condition = this?.condition.toCondition()
)

fun ConditionEntity?.toCondition() = Condition(
  text = this?.text.orEmpty(),
  icon = this?.icon?.replaceFirst("//", "https://", true).orEmpty()
)
