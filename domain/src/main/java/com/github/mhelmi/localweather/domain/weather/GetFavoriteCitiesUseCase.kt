package com.github.mhelmi.localweather.domain.weather

import javax.inject.Inject

class GetFavoriteCitiesUseCase @Inject constructor(
  private val weatherRepository: WeatherRepository
) {
  operator fun invoke() = weatherRepository.getFavoriteCities()
}