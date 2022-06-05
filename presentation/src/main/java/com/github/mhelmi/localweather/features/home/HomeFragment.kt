package com.github.mhelmi.localweather.features.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import android.widget.ToggleButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.github.mhelmi.localweather.R
import com.github.mhelmi.localweather.common.base.BaseFragment
import com.github.mhelmi.localweather.common.constants.FragmentResult
import com.github.mhelmi.localweather.common.ext.collectFlow
import com.github.mhelmi.localweather.common.ext.getCityName
import com.github.mhelmi.localweather.common.location.LocationService
import com.github.mhelmi.localweather.databinding.FragmentHomeBinding
import com.github.mhelmi.localweather.di.annotations.MainDispatcher
import com.github.mhelmi.localweather.domain.model.City
import com.github.mhelmi.localweather.features.weather_details.WeatherDetailsInteractor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

  private val homeViewModel: HomeViewModel by viewModels()

  @Inject
  lateinit var locationService: LocationService

  @MainDispatcher
  @Inject
  lateinit var mainDispatcher: CoroutineDispatcher

  private val weatherDetailsInteractor: WeatherDetailsInteractor by lazy {
    childFragmentManager.findFragmentById(
      R.id.fragment_container_weather_details
    ) as WeatherDetailsInteractor
  }

  private val requestLocationPermissionLauncher by lazy {
    registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { grantedPermissions ->
      val isGranted = grantedPermissions.any { it.value }
      if (isGranted) {
        getCurrentLocation()
      } else {
        Toast.makeText(
          context,
          getString(R.string.error_with_location_permissions),
          Toast.LENGTH_SHORT
        ).show()
      }
    }
  }

  override fun onBind(
    inflater: LayoutInflater,
    container: ViewGroup?
  ): FragmentHomeBinding {
    setHasOptionsMenu(true)
    return FragmentHomeBinding.inflate(inflater, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    checkLocationPermission()
    observeUiState()
    getFragmentResults()
  }

  private fun checkLocationPermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      if (
        ContextCompat.checkSelfPermission(
          requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        &&
        ContextCompat.checkSelfPermission(
          requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
      ) {
        getCurrentLocation()
      } else {
        requestLocationPermissionLauncher.launch(
          arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
          )
        )
      }
    } else {
      getCurrentLocation()
    }
  }

  private fun getCurrentLocation() {
    locationService.getCurrentLocation()
      .onEach {
        val cityName = it.getCityName(requireContext())
        weatherDetailsInteractor.loadWeatherForecast(cityName)
      }
      .catch {
        Toast.makeText(context, getString(R.string.error_with_location), Toast.LENGTH_LONG).show()
      }
      .flowOn(mainDispatcher)
      .launchIn(lifecycleScope)
  }

  private fun getFragmentResults() {
    childFragmentManager.setFragmentResultListener(
      FragmentResult.CURRENT_CITY,
      viewLifecycleOwner
    ) { _, bundle ->
      val city = bundle.getParcelable<City>(FragmentResult.BUNDLE_KEY_CURRENT_CITY)
      city?.let { homeViewModel.setCurrentCity(city) }
    }
  }

  private fun observeUiState() {
    collectFlow {
      launch { homeViewModel.isCelsiusFlow.collect(::handleIsCelsiusFlow) }
    }
  }

  private fun handleIsCelsiusFlow(isCelsius: Boolean) {
    activity?.invalidateOptionsMenu()
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.settings_menu, menu)
    super.onCreateOptionsMenu(menu, inflater)
  }

  override fun onPrepareOptionsMenu(menu: Menu) {
    super.onPrepareOptionsMenu(menu)
    val menuItem = menu.findItem(R.id.action_temp_unit_toggle)
    val toggleButton = menuItem?.actionView as? ToggleButton
    toggleButton?.isChecked = homeViewModel.isCelsius()
    toggleButton?.setOnCheckedChangeListener { _, isChecked ->
      homeViewModel.setTempUnit(isChecked)
    }
  }
}