package com.github.mhelmi.localweather.data.session

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.github.mhelmi.localweather.common.toGson
import com.github.mhelmi.localweather.common.toObject
import com.github.mhelmi.localweather.data.weather.source.local.model.CityEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherDataStore @Inject constructor(@ApplicationContext private val context: Context) {

  private val Context._dataStore: DataStore<Preferences> by preferencesDataStore(
    name = PREFERENCE_DATE_STORE_NAME
  )

  private val dataStore: DataStore<Preferences> = context._dataStore

  val isCelsius: Flow<Boolean> = dataStore.data
    .map { it[IS_CELSIUS] ?: true }
    .distinctUntilChanged()

  suspend fun setTempUnit(isCelsius: Boolean) = dataStore.edit {
    it[IS_CELSIUS] = isCelsius
  }

  val currentCity: Flow<CityEntity> = dataStore.data
    .map { it[CURRENT_CITY] ?: "" }
    .map { it.toObject(CityEntity::class.java) }
    .distinctUntilChanged()

  suspend fun setCurrentCity(cityEntity: CityEntity) = dataStore.edit {
    it[CURRENT_CITY] = cityEntity.toGson()
  }

  suspend fun clearData() {
    dataStore.edit { it.clear() }
  }

  companion object {
    private const val PREFERENCE_DATE_STORE_NAME = "localweather_preferences"
    private val IS_CELSIUS = booleanPreferencesKey("pref_temp_unit")
    private val CURRENT_CITY = stringPreferencesKey("pref_current_city")
  }
}
