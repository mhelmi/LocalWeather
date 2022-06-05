package com.github.mhelmi.localweather.domain.weather

import com.github.mhelmi.localweather.domain.model.City
import com.github.mhelmi.localweather.domain.model.CurrentWeatherForecast
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
  fun getWeatherForecast(cityName: String): Flow<CurrentWeatherForecast>

  fun getFavoriteCities(): Flow<List<City>>

  fun searchForCity(cityName: String): Flow<List<City>>

  suspend fun updateCity(city: City)

  suspend fun saveCurrentCity(city: City)

  fun getCurrentCity(): Flow<City>

  fun isCelsius(): Flow<Boolean>

  suspend fun setTempUnit(isCelsius: Boolean)
}