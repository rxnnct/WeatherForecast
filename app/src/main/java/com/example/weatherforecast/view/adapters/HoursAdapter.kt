package com.example.weatherforecast.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecast.R
import com.example.weatherforecast.databinding.HoursListItemBinding
import com.example.weatherforecast.model.WeatherData.Forecast.Forecastday.Hour

class HoursAdapter : ListAdapter<Hour, HoursAdapter.Holder>(ItemComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context).inflate(R.layout.hours_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = HoursListItemBinding.bind(view)

        fun bind(item: Hour) = with(binding) {
            tvHour.text = item.time
            tvCondition.text = item.condition.text
            tvTemperature.text = item.temp_c.toString()
            // TODO: url
        }
    }

    class ItemComparator : DiffUtil.ItemCallback<Hour>() {
        override fun areItemsTheSame(oldItem: Hour, newItem: Hour): Boolean {
            return oldItem.time == newItem.time
        }

        override fun areContentsTheSame(oldItem: Hour, newItem: Hour): Boolean {
            return oldItem == newItem
        }
    }
}