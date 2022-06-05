package com.github.mhelmi.localweather.features.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mhelmi.localweather.common.ext.toUiState
import com.github.mhelmi.localweather.common.state.UiState
import com.github.mhelmi.localweather.di.annotations.IoDispatcher
import com.github.mhelmi.localweather.domain.model.City
import com.github.mhelmi.localweather.domain.weather.SearchForCityUseCase
import com.github.mhelmi.localweather.domain.weather.UpdateCityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
  private val searchForCityUseCase: SearchForCityUseCase,
  private val updateCityUseCase: UpdateCityUseCase,
  @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {


  private val _uiState = MutableStateFlow<UiState<List<City>>>(UiState.Init())
  val uiState = _uiState.asStateFlow()

  fun searchForCity(query: String) {
    if (query.isEmpty()) {
      _uiState.value = UiState.Empty()
      return
    }
    searchForCityUseCase(query)
      .onStart { _uiState.value = UiState.Loading() }
      .onEach { _uiState.value = UiState.Success(it) }
      .catch { _uiState.value = it.toUiState() }
      .flowOn(ioDispatcher)
      .launchIn(viewModelScope)
  }

  fun updateCity(city: City) = viewModelScope.launch(ioDispatcher) {
    updateCityUseCase(city)
  }

}