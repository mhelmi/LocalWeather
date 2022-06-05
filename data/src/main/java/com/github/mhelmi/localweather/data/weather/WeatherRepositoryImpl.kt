package com.github.mhelmi.localweather.data.weather

import com.github.mhelmi.localweather.data.weather.source.WeatherLocalSource
import com.github.mhelmi.localweather.data.weather.source.WeatherRemoteSource
import com.github.mhelmi.localweather.data.weather.source.local.mapper.toCity
import com.github.mhelmi.localweather.data.weather.source.local.mapper.toCityEntity
import com.github.mhelmi.localweather.data.weather.source.local.mapper.toCityList
import com.github.mhelmi.localweather.data.weather.source.local.model.CityEntity
import com.github.mhelmi.localweather.data.weather.source.remote.mapper.toCurrentWeatherEntity
import com.github.mhelmi.localweather.data.weather.source.remote.mapper.toCurrentWeatherForecast
import com.github.mhelmi.localweather.data.weather.source.remote.mapper.toForecastDayEntityList
import com.github.mhelmi.localweather.domain.model.City
import com.github.mhelmi.localweather.domain.model.CurrentWeatherForecast
import com.github.mhelmi.localweather.domain.weather.WeatherRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImpl @Inject constructor(
  private val weatherLocalSource: WeatherLocalSource,
  private val weatherRemoteSource: WeatherRemoteSource
) : WeatherRepository {

  override fun getWeatherForecast(cityName: String): Flow<CurrentWeatherForecast> {
    return weatherRemoteSource.getWeatherForecast(cityName = cityName)
      .onEach {
        // check isFavorite to no override it with the remote version
        val oldCityEntity = getCityByName(it.city?.name.orEmpty())
        weatherLocalSource.saveCityEntity(
          it.city.toCityEntity().copy(isFavorite = oldCityEntity?.isFavorite ?: false)
        )
        weatherLocalSource.saveCurrentWeatherEntity(
          it.currentWeatherRemote.toCurrentWeatherEntity(
            it.city?.name.orEmpty(),
            it.city?.region.orEmpty(),
            it.city?.country.orEmpty()
          )
        )
        weatherLocalSource.saveForecastDayEntityList(
          it.forecast?.forecastDaysList.toForecastDayEntityList(
            it.city?.name.orEmpty(),
            it.city?.region.orEmpty(),
            it.city?.country.orEmpty()
          )
        )
      }
      .flatMapLatest { response ->
        weatherLocalSource.isCelsius()
          .map { isCelsius -> response.toCurrentWeatherForecast(isCelsius) }
      }
      .catch {
        val favoriteCity = getCityByName(cityName)
        val currentCity = getCurrentCity().firstOrNull()
        val city: CityEntity? = favoriteCity ?: if (currentCity != null) {
          currentCity.toCityEntity()
        } else null

        if (city == null) {
          throw it
        } else {
          emitAll(
            weatherLocalSource.getCurrentWeatherForecastByCityName(
              city.name,
              city.region,
              city.country,
              city.lat,
              city.lon
            )
              .take(1)
              .flatMapLatest { entity ->
                weatherLocalSource.isCelsius()
                  .map { isCelsius -> entity.toCurrentWeatherForecast(isCelsius) }
              }
          )
        }
      }
  }

  override fun getFavoriteCities(): Flow<List<City>> {
    return weatherLocalSource.getFavoriteCities()
      .map { it.toCityList() }
  }

  override fun searchForCity(cityName: String): Flow<List<City>> {
    return weatherRemoteSource.searchForCity(cityName = cityName)
      .map { it.toCityList() }
  }

  override suspend fun updateCity(city: City) {
    weatherLocalSource.saveCityEntity(city.toCityEntity())
  }

  override suspend fun saveCurrentCity(city: City) {
    // check isFavorite to no override it with the remote version
    val oldCityEntity = getCityByName(city.name)
    weatherLocalSource.saveCurrentCity(
      city.toCityEntity().copy(isFavorite = oldCityEntity?.isFavorite ?: false)
    )
  }

  override fun getCurrentCity(): Flow<City> = weatherLocalSource.getCurrentCity()
    .map { it.toCity() }

  override fun isCelsius(): Flow<Boolean> = weatherLocalSource.isCelsius()

  override suspend fun setTempUnit(isCelsius: Boolean) = weatherLocalSource.setTempUnit(isCelsius)

  private suspend fun getCityByName(cityName: String): CityEntity? {
    return weatherLocalSource.getCityByName(cityName).firstOrNull()
  }
}