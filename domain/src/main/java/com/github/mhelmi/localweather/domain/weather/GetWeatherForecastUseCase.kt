package com.github.mhelmi.localweather.domain.weather

import javax.inject.Inject

class GetWeatherForecastUseCase @Inject constructor(
  private val weatherRepository: WeatherRepository
) {
  operator fun invoke(cityName: String) = weatherRepository.getWeatherForecast(cityName)
}