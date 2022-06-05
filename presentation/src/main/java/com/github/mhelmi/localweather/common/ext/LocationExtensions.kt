package com.github.mhelmi.localweather.common.ext

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import java.util.*

fun Location.getCityName(context: Context): String {
  val geoCoder = Geocoder(context, Locale.getDefault())
  val addresses: List<Address> = geoCoder.getFromLocation(this.latitude, this.longitude, 1)
  return addresses[0].adminArea
}

fun Location.getCityAndCountry(context: Context): Pair<String, String> {
  val geoCoder = Geocoder(context, Locale.getDefault())
  val addresses: List<Address> = geoCoder.getFromLocation(this.latitude, this.longitude, 1)
  val cityName: String = addresses[0].adminArea
  val countryCode = addresses[0].countryCode
  return Pair(cityName, countryCode)
}