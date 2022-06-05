package com.github.mhelmi.localweather.data.weather.source.local.model

import androidx.room.Entity

@Entity(tableName = "city", primaryKeys = ["name", "region", "country"])
data class CityEntity(
  val name: String,
  val region: String,
  val country: String,
  val lat: Double,
  val lon: Double,
  val isFavorite: Boolean = false
)