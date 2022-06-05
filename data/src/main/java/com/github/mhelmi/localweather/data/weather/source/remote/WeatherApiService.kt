package com.github.mhelmi.localweather.data.weather.source.remote

import com.github.mhelmi.localweather.data.BuildConfig
import com.github.mhelmi.localweather.data.weather.source.remote.model.CityRemote
import com.github.mhelmi.localweather.data.weather.source.remote.model.GetWeatherForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
  @GET("forecast.json")
  suspend fun getWeatherForecast(
    @Query("q") query: String, // Pass US Zipcode, UK Postcode, Canada Postalcode, IP address, Latitude/Longitude (decimal degree) or city name.
    @Query("days") forecastDays: Int = 10, // from 1 to 10
    @Query("key") apiKey: String = BuildConfig.WEATHER_API_KEY,
    @Query("aqi") airQuality: String = "no",
    @Query("alerts") alerts: String = "no",
  ): GetWeatherForecastResponse

  @GET("search.json")
  suspend fun searchForCity(
    @Query("q") query: String, // Pass US Zipcode, UK Postcode, Canada Postalcode, IP address, Latitude/Longitude (decimal degree) or city name.
    @Query("key") apiKey: String = BuildConfig.WEATHER_API_KEY,
  ): List<CityRemote>
}