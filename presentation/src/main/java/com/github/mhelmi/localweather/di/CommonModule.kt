package com.github.mhelmi.localweather.di

import com.github.mhelmi.localweather.common.location.LocationService
import com.github.mhelmi.localweather.common.location.LocationServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Muhammad Helmi on 04/6/2022.
 * m.helmi.khalil@gmail.com
 */

@InstallIn(SingletonComponent::class)
@Module
abstract class CommonModule {

  @Binds
  @Singleton
  abstract fun bindLocationService(locationService: LocationServiceImpl): LocationService
}