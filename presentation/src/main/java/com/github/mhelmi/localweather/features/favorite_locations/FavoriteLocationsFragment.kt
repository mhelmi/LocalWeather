package com.github.mhelmi.localweather.features.favorite_locations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mhelmi.localweather.MobileNavigationDirections
import com.github.mhelmi.localweather.R
import com.github.mhelmi.localweather.common.base.BaseFragment
import com.github.mhelmi.localweather.common.ext.collectFlow
import com.github.mhelmi.localweather.common.state.UiState
import com.github.mhelmi.localweather.databinding.FragmentFavoriteLocationsBinding
import com.github.mhelmi.localweather.domain.model.City
import com.github.mhelmi.localweather.features.search.adapter.CitiesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteLocationsFragment : BaseFragment<FragmentFavoriteLocationsBinding>(),
  CitiesAdapter.CityActionsListener {

  private val favoriteLocationsViewModel: FavoriteLocationsViewModel by viewModels()

  private lateinit var citiesAdapter: CitiesAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    favoriteLocationsViewModel.getFavoriteCities()
  }

  override fun onBind(
    inflater: LayoutInflater,
    container: ViewGroup?
  ): FragmentFavoriteLocationsBinding {
    return FragmentFavoriteLocationsBinding.inflate(inflater, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupCitiesRecyclerView()
    observeUiState()
  }

  private fun setupCitiesRecyclerView() = binding?.rvFavoriteCities?.apply {
    citiesAdapter = CitiesAdapter(this@FavoriteLocationsFragment)
    setHasFixedSize(true)
    layoutManager = LinearLayoutManager(context)
    adapter = citiesAdapter
  }

  private fun observeUiState() {
    collectFlow {
      launch { favoriteLocationsViewModel.uiState.collect(::handleFavoriteCitiesState) }
    }
  }

  private fun handleFavoriteCitiesState(state: UiState<List<City>>) = binding?.apply {
    setLoading(state is UiState.Init || state is UiState.Loading)
    rvFavoriteCities.isVisible = state is UiState.Success
    tvError.isVisible = state is UiState.Error || state is UiState.Empty || state is UiState.Init
    when (state) {
      is UiState.Success -> citiesAdapter.submitList(state.data)
      is UiState.Init, is UiState.Empty -> tvError.text =
        getString(R.string.message_you_can_view_your_favorite_cities_here)
      is UiState.Error -> tvError.text = state.message
      else -> Unit
    }
  }

  override fun onCityClick(city: City) {
    navController.navigate(
      MobileNavigationDirections.actionGlobalToCityWeatherDetailsFragment(city.name)
    )
  }

  override fun onUpdateCityFavorite(city: City) {
    favoriteLocationsViewModel.updateCity(city)
  }

}