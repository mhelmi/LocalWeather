package com.github.mhelmi.localweather.features.city_weather_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.github.mhelmi.localweather.R
import com.github.mhelmi.localweather.common.base.BaseFragment
import com.github.mhelmi.localweather.databinding.FragmentCityWeatherDetailsBinding
import com.github.mhelmi.localweather.features.weather_details.WeatherDetailsInteractor

class CityWeatherDetailsFragment : BaseFragment<FragmentCityWeatherDetailsBinding>() {

  private val args: CityWeatherDetailsFragmentArgs by navArgs()

  private val weatherDetailsInteractor: WeatherDetailsInteractor by lazy {
    childFragmentManager.findFragmentById(
      R.id.fragment_container_weather_details
    ) as WeatherDetailsInteractor
  }

  override fun onBind(
    inflater: LayoutInflater,
    container: ViewGroup?
  ): FragmentCityWeatherDetailsBinding {
    return FragmentCityWeatherDetailsBinding.inflate(inflater, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    weatherDetailsInteractor.loadWeatherForecast(args.cityName)
  }
}