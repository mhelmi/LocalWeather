package com.github.mhelmi.localweather.features.weather_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mhelmi.localweather.common.ext.toUiState
import com.github.mhelmi.localweather.common.state.UiState
import com.github.mhelmi.localweather.di.annotations.IoDispatcher
import com.github.mhelmi.localweather.domain.model.CurrentWeatherForecast
import com.github.mhelmi.localweather.domain.weather.GetWeatherForecastUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class WeatherDetailsViewModel @Inject constructor(
  private val getWeatherForecastUseCase: GetWeatherForecastUseCase,
  @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

  private val _uiState = MutableStateFlow<UiState<CurrentWeatherForecast>>(UiState.Init())
  val uiState = _uiState.asStateFlow()

  fun loadWeatherForecast(cityName: String) {
    getWeatherForecastUseCase(cityName)
      .onStart { _uiState.value = UiState.Loading() }
      .onEach { _uiState.value = UiState.Success(it) }
      .onEmpty { _uiState.value = UiState.Empty() }
      .catch { _uiState.value = it.toUiState() }
      .flowOn(ioDispatcher)
      .launchIn(viewModelScope)
  }

}