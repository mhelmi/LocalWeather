package com.github.mhelmi.localweather.data.weather.source

import com.github.mhelmi.localweather.data.weather.source.local.model.CityEntity
import com.github.mhelmi.localweather.data.weather.source.local.model.CurrentWeatherEntity
import com.github.mhelmi.localweather.data.weather.source.local.model.CurrentWeatherForecastEntity
import com.github.mhelmi.localweather.data.weather.source.local.model.ForecastDayEntity
import com.github.mhelmi.localweather.data.weather.source.remote.model.CityRemote
import com.github.mhelmi.localweather.data.weather.source.remote.model.GetWeatherForecastResponse
import kotlinx.coroutines.flow.Flow

interface WeatherLocalSource {
  fun getCurrentWeatherForecastByCityName(
    cityName: String,
    region: String,
    country: String,
    lat: Double,
    lon: Double,
  ): Flow<CurrentWeatherForecastEntity>

  fun getFavoriteCities(): Flow<List<CityEntity>>

  suspend fun saveCityEntity(cityEntity: CityEntity)

  suspend fun saveCurrentWeatherEntity(currentWeatherEntity: CurrentWeatherEntity)

  suspend fun saveForecastDayEntityList(forecastDayEntityList: List<ForecastDayEntity>)

  fun getCityByName(cityName: String): Flow<CityEntity>

  suspend fun saveCurrentCity(cityEntity: CityEntity)

  fun getCurrentCity(): Flow<CityEntity>

  fun isCelsius(): Flow<Boolean>

  suspend fun setTempUnit(isCelsius: Boolean)
}

interface WeatherRemoteSource {
  fun getWeatherForecast(cityName: String): Flow<GetWeatherForecastResponse>

  fun searchForCity(cityName: String): Flow<List<CityRemote>>
}