package com.github.mhelmi.localweather.data.weather.source.local

import com.github.mhelmi.localweather.data.session.WeatherDataStore
import com.github.mhelmi.localweather.data.weather.source.WeatherLocalSource
import com.github.mhelmi.localweather.data.weather.source.local.dao.CityDao
import com.github.mhelmi.localweather.data.weather.source.local.dao.ForecastDao
import com.github.mhelmi.localweather.data.weather.source.local.dao.WeatherDao
import com.github.mhelmi.localweather.data.weather.source.local.model.CityEntity
import com.github.mhelmi.localweather.data.weather.source.local.model.CurrentWeatherEntity
import com.github.mhelmi.localweather.data.weather.source.local.model.CurrentWeatherForecastEntity
import com.github.mhelmi.localweather.data.weather.source.local.model.ForecastDayEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherLocalSourceImpl @Inject constructor(
  private val cityDao: CityDao,
  private val weatherDao: WeatherDao,
  private val forecastDao: ForecastDao,
  private val weatherDataStore: WeatherDataStore
) : WeatherLocalSource {
  override fun getCurrentWeatherForecastByCityName(
    cityName: String,
    region: String,
    country: String,
    lat: Double,
    lon: Double,
  ): Flow<CurrentWeatherForecastEntity> {
    return cityDao.getCurrentWeatherForecastByCity(cityName, region, country, lat, lon)
  }

  override fun getFavoriteCities(): Flow<List<CityEntity>> = cityDao.getFavoriteCities()

  override suspend fun saveCityEntity(cityEntity: CityEntity) {
    cityDao.insert(cityEntity)
  }

  override suspend fun saveCurrentWeatherEntity(currentWeatherEntity: CurrentWeatherEntity) {
    weatherDao.insert(currentWeatherEntity)
  }

  override suspend fun saveForecastDayEntityList(forecastDayEntityList: List<ForecastDayEntity>) {
    forecastDao.insert(forecastDayEntityList)
  }

  override fun getCityByName(cityName: String): Flow<CityEntity> {
    return cityDao.getCityByName(cityName)
  }

  override suspend fun saveCurrentCity(cityEntity: CityEntity) {
    weatherDataStore.setCurrentCity(cityEntity)
  }

  override fun getCurrentCity(): Flow<CityEntity> = weatherDataStore.currentCity

  override fun isCelsius(): Flow<Boolean> = weatherDataStore.isCelsius

  override suspend fun setTempUnit(isCelsius: Boolean) {
    weatherDataStore.setTempUnit(isCelsius)
  }

}