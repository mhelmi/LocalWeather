package com.github.mhelmi.localweather.features.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mhelmi.localweather.MobileNavigationDirections
import com.github.mhelmi.localweather.R
import com.github.mhelmi.localweather.common.base.BaseFragment
import com.github.mhelmi.localweather.common.ext.collectFlow
import com.github.mhelmi.localweather.common.ext.onQueryChanges
import com.github.mhelmi.localweather.common.state.UiState
import com.github.mhelmi.localweather.databinding.FragmentSearchBinding
import com.github.mhelmi.localweather.domain.model.City
import com.github.mhelmi.localweather.features.search.adapter.CitiesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(), CitiesAdapter.CityActionsListener {

  private val searchViewModel: SearchViewModel by viewModels()

  private lateinit var citiesAdapter: CitiesAdapter

  override fun onBind(
    inflater: LayoutInflater,
    container: ViewGroup?
  ): FragmentSearchBinding {
    return FragmentSearchBinding.inflate(inflater, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupCitiesRecyclerView()
    observeUiState()
  }

  private fun setupCitiesRecyclerView() = binding?.rvCities?.apply {
    citiesAdapter = CitiesAdapter(this@SearchFragment)
    setHasFixedSize(true)
    layoutManager = LinearLayoutManager(context)
    adapter = citiesAdapter
  }

  override fun setOnTextChangeListeners() = binding?.apply {
    searchView.apply {
      onQueryChanges(lifecycleCoroutineScope = lifecycleScope) {
        searchViewModel.searchForCity(it)
      }
    }
  }

  private fun observeUiState() {
    collectFlow {
      launch { searchViewModel.uiState.collect(::handleCitiesState) }
    }
  }

  private fun handleCitiesState(state: UiState<List<City>>) = binding?.apply {
    setLoading(state is UiState.Loading)
    rvCities.isVisible = state is UiState.Success
    tvError.isVisible = state is UiState.Error || state is UiState.Empty || state is UiState.Init
    when (state) {
      is UiState.Success -> citiesAdapter.submitList(state.data)
      is UiState.Init -> tvError.text =
        getString(R.string.message_you_can_search_using_city_name_or_country_name)
      is UiState.Error -> tvError.text = state.message
      is UiState.Empty -> tvError.text = getString(R.string.error_no_cities_found)
      else -> Unit
    }
  }

  override fun onCityClick(city: City) {
    navController.navigate(
      MobileNavigationDirections.actionGlobalToCityWeatherDetailsFragment(city.name)
    )
  }

  override fun onUpdateCityFavorite(city: City) {
    searchViewModel.updateCity(city)
  }

}