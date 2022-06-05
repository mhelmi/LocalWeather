package com.github.mhelmi.localweather.domain.weather

import javax.inject.Inject

class SetTempUnitUseCase @Inject constructor(
  private val weatherRepository: WeatherRepository
) {
  suspend operator fun invoke(isCelsius: Boolean) = weatherRepository.setTempUnit(isCelsius)
}