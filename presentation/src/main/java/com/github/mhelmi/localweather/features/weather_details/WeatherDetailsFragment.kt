package com.github.mhelmi.localweather.features.weather_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.github.mhelmi.localweather.R
import com.github.mhelmi.localweather.common.base.BaseFragment
import com.github.mhelmi.localweather.common.constants.FragmentResult
import com.github.mhelmi.localweather.common.ext.collectFlow
import com.github.mhelmi.localweather.common.state.UiState
import com.github.mhelmi.localweather.common.toGson
import com.github.mhelmi.localweather.databinding.FragmentWeatherDetailsBinding
import com.github.mhelmi.localweather.domain.model.City
import com.github.mhelmi.localweather.domain.model.CurrentWeatherForecast
import com.github.mhelmi.localweather.features.weather_details.adapter.WeatherForecastAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WeatherDetailsFragment : BaseFragment<FragmentWeatherDetailsBinding>(),
  WeatherDetailsInteractor {

  private val weatherDetailsViewModel: WeatherDetailsViewModel by viewModels()

  private lateinit var weatherForecastAdapter: WeatherForecastAdapter

  override fun onBind(
    inflater: LayoutInflater,
    container: ViewGroup?
  ): FragmentWeatherDetailsBinding {
    return FragmentWeatherDetailsBinding.inflate(inflater, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupForecastRecyclerView()
    observeUiState()
  }

  private fun setupForecastRecyclerView() = binding?.rvForecastDays?.apply {
    layoutManager = LinearLayoutManager(context)
    setHasFixedSize(true)
    weatherForecastAdapter = WeatherForecastAdapter()
    adapter = weatherForecastAdapter
  }

  override fun loadWeatherForecast(cityName: String) {
    weatherDetailsViewModel.loadWeatherForecast(cityName)
  }

  private fun observeUiState() {
    collectFlow {
      launch { weatherDetailsViewModel.uiState.collect(::handleCurrentWeatherForecastState) }
    }
  }

  private fun handleCurrentWeatherForecastState(state: UiState<CurrentWeatherForecast>) =
    binding?.apply {
      setLoading(state is UiState.Loading)
      contentScrollView.isVisible = state is UiState.Success
      tvError.isVisible = state is UiState.Error || state is UiState.Init
      when (state) {
        is UiState.Success -> populateCurrentWeatherForecastDetails(state.data)
        is UiState.Init -> tvError.text =
          getString(R.string.message_you_can_see_weather_details_here)
        is UiState.Error -> tvError.text = state.message
        else -> Unit
      }
    }

  private fun populateCurrentWeatherForecastDetails(data: CurrentWeatherForecast) = binding?.apply {
    println("CurrentWeatherForecast: ${data.toGson()}")
    updateCurrentCity(data.city)
    data.city.let {
      tvCityName.text = it.name
    }
    data.currentWeather.let {
      tvTemp.text = getString(R.string.temp_, it.temp.toString())
      it.condition.let { condition ->
        tvWeatherCondition.text = condition.text
        Glide.with(requireContext())
          .load(condition.icon)
          .into(ivWeatherCondition)
      }
      tvFeelsLike.text = getString(R.string.feels_like_, it.feelsLike.toString())
      tvWindValue.text = getString(R.string.wind_speed_, it.windKph.toString())
      tvHumidityValue.text = getString(R.string.humidity_percent_, it.humidity.toString()) + "%"
    }
    weatherForecastAdapter.submitList(data.forecastDaysList)
  }

  private fun updateCurrentCity(city: City) {
    println("updateCurrentCity")
    setFragmentResult(
      FragmentResult.CURRENT_CITY,
      bundleOf(FragmentResult.BUNDLE_KEY_CURRENT_CITY to city)
    )
  }
}