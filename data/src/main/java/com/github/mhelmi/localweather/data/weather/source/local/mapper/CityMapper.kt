package com.github.mhelmi.localweather.data.weather.source.local.mapper

import com.github.mhelmi.localweather.data.weather.source.local.model.CityEntity
import com.github.mhelmi.localweather.data.weather.source.remote.model.CityRemote
import com.github.mhelmi.localweather.domain.model.City

fun List<CityRemote>?.toCityList() = this?.map {
  it.toCity()
}.orEmpty()

fun CityRemote?.toCity() = City(
  name = this?.name ?: "",
  region = this?.region ?: "",
  country = this?.country ?: "",
  lat = this?.lat ?: 0.0,
  lon = this?.lon ?: 0.0,
  isFavorite = false
)

fun CityRemote?.toCityEntity() = CityEntity(
  name = this?.name ?: "",
  region = this?.region ?: "",
  country = this?.country ?: "",
  lat = this?.lat ?: 0.0,
  lon = this?.lon ?: 0.0,
  isFavorite = false
)

@JvmName("toCityListCityEntity")
fun List<CityEntity>?.toCityList() = this?.map {
  it.toCity()
}.orEmpty()

fun CityEntity?.toCity() = City(
  name = this?.name ?: "",
  region = this?.region ?: "",
  country = this?.country ?: "",
  lat = this?.lat ?: 0.0,
  lon = this?.lon ?: 0.0,
  isFavorite = this?.isFavorite ?: false
)

fun City?.toCityEntity() = CityEntity(
  name = this?.name ?: "",
  region = this?.region ?: "",
  country = this?.country ?: "",
  lat = this?.lat ?: 0.0,
  lon = this?.lon ?: 0.0,
  isFavorite = this?.isFavorite ?: false
)