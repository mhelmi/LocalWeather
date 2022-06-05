package com.github.mhelmi.localweather.features.weather_details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.mhelmi.localweather.R
import com.github.mhelmi.localweather.databinding.ItemWeatherForecastBinding
import com.github.mhelmi.localweather.domain.model.ForecastDay

class WeatherForecastAdapter :
  ListAdapter<ForecastDay, WeatherForecastAdapter.WeatherForecastViewHolder>(ForecastDayDiffCallBack) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherForecastViewHolder {
    return WeatherForecastViewHolder(
      ItemWeatherForecastBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
      )
    )
  }

  override fun onBindViewHolder(holder: WeatherForecastViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  inner class WeatherForecastViewHolder(
    private val binding: ItemWeatherForecastBinding
  ) : RecyclerView.ViewHolder(binding.root) {

    private val context = binding.root.context

    fun bind(forecastDay: ForecastDay) = binding.apply {
      tvDate.text = forecastDay.date
      forecastDay.dayWeather.let {
        tvTemp.text = context.getString(R.string.temp_, it.avgTemp.toString())
        Glide.with(context)
          .load(it.condition?.icon)
          .into(ivWeatherCondition)
        tvHumidity.text =
          context.getString(R.string.humidity_percent_, it.avgHumidity.toString()) + "%"
        tvWind.text = context.getString(R.string.wind_speed_, it.maxWindKph.toString())
      }
    }
  }

  companion object {
    val ForecastDayDiffCallBack = object : DiffUtil.ItemCallback<ForecastDay>() {
      override fun areItemsTheSame(oldItem: ForecastDay, newItem: ForecastDay): Boolean {
        return oldItem.date == newItem.date
      }

      override fun areContentsTheSame(oldItem: ForecastDay, newItem: ForecastDay): Boolean {
        return oldItem.date == newItem.date && oldItem.dayWeather == newItem.dayWeather
      }
    }
  }
}