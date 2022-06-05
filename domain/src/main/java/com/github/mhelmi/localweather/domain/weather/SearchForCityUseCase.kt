package com.github.mhelmi.localweather.domain.weather

import javax.inject.Inject

class SearchForCityUseCase @Inject constructor(
  private val weatherRepository: WeatherRepository
) {
  operator fun invoke(query: String) = weatherRepository.searchForCity(query)
}