package com.github.mhelmi.localweather.data.weather.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.github.mhelmi.localweather.data.base.BaseDao
import com.github.mhelmi.localweather.data.weather.source.local.model.CurrentWeatherEntity

@Dao
abstract class WeatherDao : BaseDao<CurrentWeatherEntity> {
//  @Query("SELECT * FROM weather WHERE id IN(:cityId)")
//  abstract fun getWeathersByCityId(cityId: Int): Flow<List<WeatherEntity>>

//  @Transaction
//  open suspend fun updateList(cityId: Int, list: List<WeatherEntity>) {
//    deleteAll(cityId)
//    insert(list)
//  }

  @Query("DELETE FROM weather WHERE id IN(:cityId)")
  abstract suspend fun deleteAll(cityId: Int)
}