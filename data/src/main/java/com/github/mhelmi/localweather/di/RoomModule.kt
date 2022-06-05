package com.github.mhelmi.localweather.di

import android.app.Application
import androidx.room.Room
import com.github.mhelmi.localweather.data.database.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

  @Provides
  @Singleton
  fun providesRoomDb(context: Application): WeatherDatabase = Room.databaseBuilder(
    context,
    WeatherDatabase::class.java,
    "weather_db"
  ).fallbackToDestructiveMigration()
    .build()

  @Provides
  @Singleton
  fun provideCityDao(db: WeatherDatabase) = db.cityDao()

  @Provides
  @Singleton
  fun provideWeatherDao(db: WeatherDatabase) = db.weatherDao()

  @Provides
  @Singleton
  fun provideForecastDao(db: WeatherDatabase) = db.forecastDao()
}