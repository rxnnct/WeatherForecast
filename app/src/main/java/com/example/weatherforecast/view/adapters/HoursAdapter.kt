package com.example.weatherforecast.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecast.R
import com.example.weatherforecast.databinding.HoursListItemBinding
import com.example.weatherforecast.model.ForecastHour
import com.squareup.picasso.Picasso

class HoursAdapter : ListAdapter<ForecastHour, HoursAdapter.Holder>(ItemComparator()) {

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

        fun bind(item: ForecastHour) = with(binding) {
            tvHour.text = item.hour.takeLast(5)
            tvCondition.text = item.condition.description
            tvTemperature.text = item.temperature
            Picasso.get().load("https:" + item.condition.imageUrl).into(ivCondition)
        }
    }

    class ItemComparator : DiffUtil.ItemCallback<ForecastHour>() {
        override fun areItemsTheSame(oldItem: ForecastHour, newItem: ForecastHour): Boolean {
            return oldItem.hour == newItem.hour
        }

        override fun areContentsTheSame(oldItem: ForecastHour, newItem: ForecastHour): Boolean {
            return oldItem == newItem
        }
    }
}