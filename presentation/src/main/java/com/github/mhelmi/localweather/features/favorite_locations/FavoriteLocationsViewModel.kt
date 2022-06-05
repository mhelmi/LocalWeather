package com.github.mhelmi.localweather.features.favorite_locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mhelmi.localweather.common.ext.toUiState
import com.github.mhelmi.localweather.common.state.UiState
import com.github.mhelmi.localweather.di.annotations.IoDispatcher
import com.github.mhelmi.localweather.domain.model.City
import com.github.mhelmi.localweather.domain.weather.GetFavoriteCitiesUseCase
import com.github.mhelmi.localweather.domain.weather.UpdateCityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteLocationsViewModel @Inject constructor(
  private val getFavoriteCitiesUseCase: GetFavoriteCitiesUseCase,
  private val updateCityUseCase: UpdateCityUseCase,
  @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

  private val _uiState = MutableStateFlow<UiState<List<City>>>(UiState.Init())
  val uiState = _uiState.asStateFlow()

  fun getFavoriteCities() {
    getFavoriteCitiesUseCase()
      .onStart { _uiState.value = UiState.Loading() }
      .onEach { _uiState.value = if (it.isEmpty()) UiState.Empty() else UiState.Success(it) }
      .catch { _uiState.value = it.toUiState() }
      .flowOn(ioDispatcher)
      .launchIn(viewModelScope)
  }

  fun updateCity(city: City) = viewModelScope.launch(ioDispatcher) {
    updateCityUseCase(city)
  }
}