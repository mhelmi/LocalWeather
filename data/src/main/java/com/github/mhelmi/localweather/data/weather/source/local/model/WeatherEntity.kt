package com.github.mhelmi.localweather.data.weather.source.local.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

data class CurrentWeatherForecastEntity(
  @Embedded val cityEntity: CityEntity?,
  @Relation(
    parentColumn = "name",
    entityColumn = "cityOwnerName"
  )
  val currentWeatherEntity: CurrentWeatherEntity?,
  @Relation(
    parentColumn = "name",
    entityColumn = "cityOwnerName"
  )
  val forecastDaysEntityList: List<ForecastDayEntity>?
)

@Entity(tableName = "weather")
data class CurrentWeatherEntity(
  @PrimaryKey(autoGenerate = true) val id: Int,
  var cityOwnerName: String,
  var regionOwnerName: String,
  var countryOwnerName: String,

  val tempC: Double?,
  val tempF: Double?,
  @Embedded val condition: ConditionEntity?,
  val windMph: Double?,
  val windKph: Double?,
  val windDegree: Double?,
  val windDir: String?,
  val humidity: Double?,
  val feelsLikeC: Double?,
  val feelsLikeF: Double?
)

@Entity(tableName = "forecast_day")
data class ForecastDayEntity(
  @PrimaryKey(autoGenerate = true) val id: Int,
  var cityOwnerName: String,
  var regionOwnerName: String,
  var countryOwnerName: String,
  val date: String?,
  val dateEpoch: Int?,
  @Embedded val dayWeatherEntity: DayWeatherEntity?
)

data class DayWeatherEntity(
  val maxTempC: Double?,
  val maxTempF: Double?,
  val minTempC: Double?,
  val minTempF: Double?,
  val avgTempC: Double?,
  val avgTempF: Double?,
  val maxWindMph: Double?,
  val maxWindKph: Double?,
  val avgHumidity: Double?,
  @Embedded val condition: ConditionEntity?
)

data class ConditionEntity(
  val text: String?,
  val icon: String?
)
