package com.github.mhelmi.localweather.di

import com.github.mhelmi.localweather.data.weather.source.remote.WeatherApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServicesModule {

  @Provides
  @Singleton
  fun providePhotosApiService(retrofit: Retrofit): WeatherApiService =
    retrofit.create(WeatherApiService::class.java)
}