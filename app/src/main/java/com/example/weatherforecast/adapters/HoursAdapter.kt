package com.example.weatherforecast.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecast.R
import com.example.weatherforecast.databinding.HoursListItemBinding
import com.example.weatherforecast.model.HourForecast

class HoursAdapter : ListAdapter<HourForecast, HoursAdapter.Holder>(ItemComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context).inflate(R.layout.hours_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = HoursListItemBinding.bind(view)

        fun bind(item: HourForecast) = with(binding) {
            tvHour.text = item.time
            tvCondition.text = item.condition.description
            tvTemperature.text = item.temperature
            // TODO: url
        }
    }

    class ItemComparator : DiffUtil.ItemCallback<HourForecast>() {
        override fun areItemsTheSame(oldItem: HourForecast, newItem: HourForecast): Boolean {
            return oldItem.time == newItem.time
        }

        override fun areContentsTheSame(oldItem: HourForecast, newItem: HourForecast): Boolean {
            return oldItem == newItem
        }
    }
}