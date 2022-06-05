package com.github.mhelmi.localweather.features.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.mhelmi.localweather.databinding.ItemCityBinding
import com.github.mhelmi.localweather.domain.model.City

class CitiesAdapter(private val cityActionsListener: CityActionsListener) :
  ListAdapter<City, CitiesAdapter.CityViewHolder>(CityDiffCallback) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
    return CityViewHolder(
      ItemCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
  }

  override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  inner class CityViewHolder(private val binding: ItemCityBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(city: City) {
      binding.tvTitle.text = city.name.plus(", ").plus(city.region).plus(", ").plus(city.country)
      binding.checkboxFavorite.isChecked = city.isFavorite
      binding.checkboxFavorite.setOnCheckedChangeListener { _, isChecked ->
        city.apply { isFavorite = isChecked }
          .also { cityActionsListener.onUpdateCityFavorite(city) }
      }
      binding.cardViewCity.setOnClickListener { cityActionsListener.onCityClick(city) }
    }
  }

  interface CityActionsListener {
    fun onCityClick(city: City)
    fun onUpdateCityFavorite(city: City)
  }

  companion object {
    val CityDiffCallback = object : DiffUtil.ItemCallback<City>() {
      override fun areItemsTheSame(oldItem: City, newItem: City) = oldItem.name == newItem.name

      override fun areContentsTheSame(oldItem: City, newItem: City) = oldItem == newItem
    }
  }
}