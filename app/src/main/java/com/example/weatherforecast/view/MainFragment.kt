package com.example.weatherforecast.view

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherforecast.R
import com.example.weatherforecast.adapters.HoursAdapter
import com.example.weatherforecast.databinding.FragmentMainBinding
import com.example.weatherforecast.model.Condition
import com.example.weatherforecast.model.HourForecast
import com.example.weatherforecast.utils.isPermissionGranted

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var hoursAdapter: HoursAdapter

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

        initWeatherListRecyclerView()
    }

    private fun initWeatherListRecyclerView() = with(binding) {
        rvHoursWeather.layoutManager = LinearLayoutManager(activity)
        hoursAdapter = HoursAdapter()
        rvHoursWeather.adapter = hoursAdapter
        // TODO: remove:
        val hoursList = listOf<HourForecast>(
            HourForecast(
                "00:00",
                "10 C",
                Condition(
                    "Rainy",
                    ""
                )
            ),
            HourForecast(
                "01:00",
                "15 C",
                Condition(
                    "Sunny",
                    ""
                )
            ),
            HourForecast(
                "01:00",
                "12 C",
                Condition(
                    "Rainy",
                    ""
                )
            )
        )
        hoursAdapter.submitList(hoursList)
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}