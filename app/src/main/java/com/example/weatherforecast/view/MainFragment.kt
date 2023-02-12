package com.example.weatherforecast.view

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherforecast.view.adapters.HoursAdapter
import com.example.weatherforecast.databinding.FragmentMainBinding
import com.example.weatherforecast.model.WeatherData
import com.example.weatherforecast.view.utils.isPermissionGranted

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

        initWeatherListRecyclerView()

        val observer = Observer<WeatherData> {
            Log.d("MyLog", it.current.temp_c.toString())
        }
        weatherViewModel.getCustomersRepository("London").observe(viewLifecycleOwner, observer)

    }

    private fun initWeatherListRecyclerView() = with(binding) {
        rvHoursWeather.layoutManager = LinearLayoutManager(activity)
        hoursAdapter = HoursAdapter()
        rvHoursWeather.adapter = hoursAdapter
        // TODO: remove:
//        val hoursList = listOf<Hour>(
//            HourForecast(
//                "00:00",
//                "10 C",
//                Hour.Condition(
//                    "Rainy",
//                    ""
//                )
//            ),
//            HourForecast(
//                "01:00",
//                "15 C",
//                Condition(
//                    "Sunny",
//                    ""
//                )
//            ),
//            HourForecast(
//                "01:00",
//                "12 C",
//                Condition(
//                    "Rainy",
//                    ""
//                )
//            )
//        )
//        hoursAdapter.submitList(hoursList)
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}