package com.github.mhelmi.localweather.di

import com.github.mhelmi.localweather.data.weather.source.WeatherRemoteSource
import com.github.mhelmi.localweather.data.weather.source.remote.WeatherRemoteSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteSourceModule {

  @Binds
  abstract fun bindWeatherRemoteSource(weatherRemoteSourceImpl: WeatherRemoteSourceImpl): WeatherRemoteSource
}