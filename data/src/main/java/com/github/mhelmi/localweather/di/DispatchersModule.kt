package com.github.mhelmi.localweather.di

import com.github.mhelmi.localweather.di.annotations.ApplicationScope
import com.github.mhelmi.localweather.di.annotations.IoDispatcher
import com.github.mhelmi.localweather.di.annotations.MainDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {
  @IoDispatcher
  @Provides
  @Singleton
  fun provideBackgroundDispatcher(): CoroutineDispatcher = Dispatchers.IO

  @MainDispatcher
  @Provides
  @Singleton
  fun provideForegroundDispatcher(): CoroutineDispatcher = Dispatchers.Main

  @ApplicationScope
  @Singleton
  @Provides
  fun providesCoroutineScope(
    @IoDispatcher ioDispatcher: CoroutineDispatcher,
  ): CoroutineScope = CoroutineScope(SupervisorJob() + ioDispatcher)
}