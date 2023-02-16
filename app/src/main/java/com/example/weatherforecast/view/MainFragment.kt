package com.example.weatherforecast.view

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherforecast.R.*
import com.example.weatherforecast.databinding.FragmentMainBinding
import com.example.weatherforecast.view.adapters.HoursAdapter
import com.example.weatherforecast.view.utils.isPermissionGranted
import com.squareup.picasso.Picasso

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var hoursAdapter: HoursAdapter
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                Toast.makeText(
                    activity,
                    "Permission ${if (it) "granted" else "denied"}",
                    Toast.LENGTH_LONG
                ).show()
            }.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        binding.ibHome.setOnClickListener {
            mainViewModel.getWeatherRepository()
        }

        updateCards()
        initWeatherListRecyclerView()
        mainViewModel.weatherLiveData.observe(viewLifecycleOwner) {
            hoursAdapter.submitList(it.forecastDays[0].forecastHours)
        }
    }

    private fun initWeatherListRecyclerView() = with(binding) {
        rvHoursWeather.layoutManager = LinearLayoutManager(activity)
        hoursAdapter = HoursAdapter()
        rvHoursWeather.adapter = hoursAdapter
    }

    private fun updateCards() = with(binding) {
        mainViewModel.weatherLiveData.observe(viewLifecycleOwner) {
            tvLocation.text = it.location
            tvTodayTemperature.text = getString(string.today_temperature, it.temperature)
            tvTodayWeatherCondition.text = it.condition.description
            Picasso.get().load(getString(string.https) + it.condition.imageUrl)
                .into(ivTodayWeatherImage)

            tvTomorrowTemperature.text = getString(
                string.next_days_temperature,
                it.forecastDays[1].minTemperature,
                it.forecastDays[1].maxTemperature
            )
            tvTomorrowWeatherCondition.text = it.forecastDays[1].condition.description
            Picasso.get().load(getString(string.https) + it.forecastDays[1].condition.imageUrl)
                .into(ivTomorrowWeatherImage)

            tvAfterTomorrowTemperature.text = getString(
                string.next_days_temperature,
                it.forecastDays[2].minTemperature,
                it.forecastDays[2].maxTemperature
            )
            tvAfterTomorrowWeatherCondition.text = it.forecastDays[2].condition.description
            Picasso.get().load(getString(string.https) + it.forecastDays[2].condition.imageUrl)
                .into(ivAfterTomorrowWeatherImage)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}