package com.github.mhelmi.localweather.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mhelmi.localweather.di.annotations.IoDispatcher
import com.github.mhelmi.localweather.domain.model.City
import com.github.mhelmi.localweather.domain.weather.GetIsCelsiusUseCase
import com.github.mhelmi.localweather.domain.weather.SaveCurrentCityUseCase
import com.github.mhelmi.localweather.domain.weather.SetTempUnitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  private val saveCurrentCityUseCase: SaveCurrentCityUseCase,
  private val setTempUnitUseCase: SetTempUnitUseCase,
  private val getIsCelsiusUseCase: GetIsCelsiusUseCase,
  @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

  private val _isCelsiusFlow = MutableStateFlow(false)
  val isCelsiusFlow = _isCelsiusFlow.asStateFlow()

  init {
    getTempUnit()
  }

  fun setCurrentCity(city: City) = viewModelScope.launch(ioDispatcher) {
    saveCurrentCityUseCase(city)
  }

  fun setTempUnit(isCelsius: Boolean) = viewModelScope.launch(ioDispatcher) {
    setTempUnitUseCase(isCelsius)
  }

  private fun getTempUnit() {
    getIsCelsiusUseCase()
      .onEach {
        _isCelsiusFlow.value = it
      }
      .flowOn(ioDispatcher)
      .launchIn(viewModelScope)
  }

  fun isCelsius() = _isCelsiusFlow.value

}