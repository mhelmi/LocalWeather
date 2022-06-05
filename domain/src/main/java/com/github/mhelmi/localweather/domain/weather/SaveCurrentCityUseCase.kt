package com.github.mhelmi.localweather.domain.weather

import com.github.mhelmi.localweather.domain.model.City
import javax.inject.Inject

class SaveCurrentCityUseCase @Inject constructor(
  private val weatherRepository: WeatherRepository
) {
  suspend operator fun invoke(city: City) = weatherRepository.saveCurrentCity(city)
}