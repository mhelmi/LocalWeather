package com.github.mhelmi.localweather.common.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * Created by Muhammad Helmi on 04/6/2022.
 * m.helmi.khalil@gmail.com
 */
@FlowPreview
@ExperimentalCoroutinesApi
class LocationServiceImpl @Inject constructor(
  @ApplicationContext private val context: Context
) : LocationService {

  override fun getCurrentLocation(): Flow<Location> {
    return flowOf(
      getLastKnownLocation(),
      getLocationUpdateOneTimeFromProvider(LocationManager.GPS_PROVIDER),
      getLocationUpdateOneTimeFromProvider(LocationManager.NETWORK_PROVIDER)
    ).flattenMerge()
      .take(1)
  }

  @SuppressLint("MissingPermission")
  private fun getLastKnownLocation(): Flow<Location> = callbackFlow {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val providers = locationManager.allProviders
    var bestLocation: Location? = null
    providers.forEach {
      val location: Location? = locationManager.getLastKnownLocation(it)
      if (location != null && (bestLocation == null || location.accuracy < bestLocation!!.accuracy)) {
        bestLocation = location
      }
    }

    if (bestLocation != null) {
      trySend(bestLocation!!)
      close()
    } else close()
    awaitClose { }
  }

  @SuppressLint("MissingPermission")
  private fun getLocationUpdateOneTimeFromProvider(provider: String): Flow<Location> =
    callbackFlow {
      val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
      val isProviderEnabled = locationManager.isProviderEnabled(provider)
      val listener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
          trySend(location)
          close()
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

        override fun onProviderEnabled(provider: String) {}

        override fun onProviderDisabled(provider: String) {}
      }
      if (isProviderEnabled) {
        locationManager.requestLocationUpdates(provider, 1000, 0F, listener)
      } else {
        close()
      }
      awaitClose { locationManager.removeUpdates(listener) }
    }
}