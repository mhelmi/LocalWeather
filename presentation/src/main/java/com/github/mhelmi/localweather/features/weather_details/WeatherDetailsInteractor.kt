package com.github.mhelmi.localweather.features.weather_details

interface WeatherDetailsInteractor {
  fun loadWeatherForecast(cityName: String)
}