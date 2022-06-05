package com.github.mhelmi.localweather.data.weather.source.remote

import com.github.mhelmi.localweather.data.weather.source.WeatherRemoteSource
import com.github.mhelmi.localweather.data.weather.source.remote.model.CityRemote
import com.github.mhelmi.localweather.data.weather.source.remote.model.GetWeatherForecastResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRemoteSourceImpl @Inject constructor(
  private val weatherApiService: WeatherApiService
) : WeatherRemoteSource {
  override fun getWeatherForecast(cityName: String): Flow<GetWeatherForecastResponse> = flow {
    emit(weatherApiService.getWeatherForecast(query = cityName))
  }

  override fun searchForCity(cityName: String): Flow<List<CityRemote>> = flow {
    emit(weatherApiService.searchForCity(query = cityName))
  }
}