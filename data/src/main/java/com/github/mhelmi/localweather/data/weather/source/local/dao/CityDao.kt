package com.github.mhelmi.localweather.data.weather.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.github.mhelmi.localweather.data.base.BaseDao
import com.github.mhelmi.localweather.data.weather.source.local.model.CityEntity
import com.github.mhelmi.localweather.data.weather.source.local.model.CurrentWeatherForecastEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CityDao : BaseDao<CityEntity> {
  @Query("SELECT * FROM city")
  abstract fun getAllCities(): Flow<List<CityEntity>>

  @Query("SELECT * FROM city WHERE isFavorite = 1")
  abstract fun getFavoriteCities(): Flow<List<CityEntity>>

  @Query("SELECT * FROM city WHERE name LIKE(:cityName)")
  abstract fun getCityByName(cityName: String): Flow<CityEntity>

  @Transaction
  @Query("SELECT * FROM city WHERE name LIKE(:cityName) AND region LIKE(:region) AND country LIKE(:country) AND lat LIKE(:lat) AND lon LIKE(:lon)")
  abstract fun getCurrentWeatherForecastByCity(
    cityName: String,
    region: String,
    country: String,
    lat: Double,
    lon: Double,
  ): Flow<CurrentWeatherForecastEntity>
}