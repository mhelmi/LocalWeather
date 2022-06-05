package com.github.mhelmi.localweather.di

import com.github.mhelmi.localweather.data.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

  @Provides
  @Singleton
  fun provideBaseUrl(): String = BuildConfig.WEATHER_BASE_URL

  @Provides
  fun provideLoggingInterceptor() =
    HttpLoggingInterceptor().apply {
      level =
        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    }

  @Provides
  @Singleton
  fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor) = OkHttpClient.Builder()
    .addInterceptor(httpLoggingInterceptor)
    .build()

  @Provides
  @Singleton
  fun provideGson(): Gson = GsonBuilder().create()

  @Provides
  @Singleton
  fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
    GsonConverterFactory.create(gson)

  @Provides
  @Singleton
  fun provideRetrofit(
    baseUrl: String,
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory,
  ): Retrofit = Retrofit.Builder()
    .baseUrl(baseUrl)
    .addConverterFactory(gsonConverterFactory)
    .client(okHttpClient)
    .build()

}