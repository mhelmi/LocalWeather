package com.github.mhelmi.localweather.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.mhelmi.localweather.data.weather.source.local.dao.CityDao
import com.github.mhelmi.localweather.data.weather.source.local.dao.ForecastDao
import com.github.mhelmi.localweather.data.weather.source.local.dao.WeatherDao
import com.github.mhelmi.localweather.data.weather.source.local.model.CityEntity
import com.github.mhelmi.localweather.data.weather.source.local.model.CurrentWeatherEntity
import com.github.mhelmi.localweather.data.weather.source.local.model.ForecastDayEntity

@Database(
  entities = [
    CityEntity::class,
    CurrentWeatherEntity::class,
    ForecastDayEntity::class,
  ],
  version = 1, exportSchema = false
)
//@TypeConverters(ConditionConverter::class, DayWeatherConverter::class)
abstract class WeatherDatabase : RoomDatabase() {
  abstract fun cityDao(): CityDao
  abstract fun weatherDao(): WeatherDao
  abstract fun forecastDao(): ForecastDao
}