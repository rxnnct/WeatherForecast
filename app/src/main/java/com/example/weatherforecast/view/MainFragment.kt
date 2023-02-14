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
    private val weatherViewModel: WeatherViewModel by activityViewModels()

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

        weatherViewModel.getWeatherRepository("London")
        updateCards()
        initWeatherListRecyclerView()
        weatherViewModel.weatherLiveData.observe(viewLifecycleOwner){
            hoursAdapter.submitList(it.forecast.forecastday[0].hour)
        }
    }

    private fun initWeatherListRecyclerView() = with(binding) {
        rvHoursWeather.layoutManager = LinearLayoutManager(activity)
        hoursAdapter = HoursAdapter()
        rvHoursWeather.adapter = hoursAdapter

    }

    private fun updateCards() = with(binding) {
        weatherViewModel.weatherLiveData.observe(viewLifecycleOwner) {
            tvLocation.text = it.location.name
            tvTodayTemperature.text = getString(string.today_temperature, it.current.temp_c)
            tvTodayWeatherCondition.text = it.current.condition.text
            Picasso.get().load(getString(string.https) + it.current.condition.icon).into(ivTodayWeatherImage)

            tvTomorrowTemperature.text = getString(
                string.next_days_temperature,
                it.forecast.forecastday[1].day.mintemp_c,
                it.forecast.forecastday[1].day.maxtemp_c
            )
            tvTomorrowWeatherCondition.text = it.forecast.forecastday[1].day.condition.text
            Picasso.get().load(getString(string.https) + it.forecast.forecastday[1].day.condition.icon)
                .into(ivTomorrowWeatherImage)

            tvAfterTomorrowTemperature.text = getString(
                string.next_days_temperature,
                it.forecast.forecastday[2].day.mintemp_c,
                it.forecast.forecastday[2].day.maxtemp_c
            )
            tvAfterTomorrowWeatherCondition.text = it.forecast.forecastday[2].day.condition.text
            Picasso.get().load(getString(string.https) + it.forecast.forecastday[2].day.condition.icon)
                .into(ivAfterTomorrowWeatherImage)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}