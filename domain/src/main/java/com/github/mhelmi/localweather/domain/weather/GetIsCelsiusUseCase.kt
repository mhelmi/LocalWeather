package com.github.mhelmi.localweather.domain.weather

import javax.inject.Inject

class GetIsCelsiusUseCase @Inject constructor(
  private val weatherRepository: WeatherRepository
) {
  operator fun invoke() = weatherRepository.isCelsius()
}