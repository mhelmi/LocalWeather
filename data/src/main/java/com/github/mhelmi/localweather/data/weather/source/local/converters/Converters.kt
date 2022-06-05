package com.github.mhelmi.localweather.data.weather.source.local.converters

import androidx.room.TypeConverter
import com.github.mhelmi.localweather.data.weather.source.local.model.ConditionEntity
import com.github.mhelmi.localweather.data.weather.source.local.model.DayWeatherEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ConditionConverter {
  private val type = object : TypeToken<ConditionEntity>() {}.type

  @TypeConverter
  fun toJson(obj: ConditionEntity): String? = Gson().toJson(obj, type)

  @TypeConverter
  fun toObject(json: String?): ConditionEntity = Gson().fromJson(json, type)
}

class DayWeatherConverter {
  private val type = object : TypeToken<DayWeatherEntity>() {}.type

  @TypeConverter
  fun toJson(obj: DayWeatherEntity): String? = Gson().toJson(obj, type)

  @TypeConverter
  fun toObject(json: String?): DayWeatherEntity = Gson().fromJson(json, type)
}
