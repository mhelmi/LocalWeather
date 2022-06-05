package com.github.mhelmi.localweather.di

import com.github.mhelmi.localweather.data.weather.source.WeatherLocalSource
import com.github.mhelmi.localweather.data.weather.source.local.WeatherLocalSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalSourceModule {

  @Binds
  abstract fun bindWeatherLocalSource(weatherLocalSourceImpl: WeatherLocalSourceImpl): WeatherLocalSource
}