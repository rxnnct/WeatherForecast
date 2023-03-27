package ru.rxnnct.weatherforecast.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ru.rxnnct.weatherforecast.R.*
import ru.rxnnct.weatherforecast.databinding.FragmentMainBinding
import ru.rxnnct.weatherforecast.view.adapters.HoursAdapter
import ru.rxnnct.weatherforecast.view.utils.isPermissionGranted
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.squareup.picasso.Picasso

class MainFragment : Fragment() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var binding: FragmentMainBinding
    private lateinit var hoursAdapter: HoursAdapter
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
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

        getLocation()
        updateCards()
        initWeatherListRecyclerView()

        binding.ibHome.setOnClickListener {
            getLocation()
        }

        mainViewModel.weatherLiveData.observe(viewLifecycleOwner) {
            hoursAdapter.submitList(it.forecast.forecastday[0].hour)
        }
    }

    // TODO: move to services
    private fun getLocation() {
        val cancellationTokenSource = CancellationTokenSource()

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationProviderClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            cancellationTokenSource.token
        )
            .addOnCompleteListener {
                mainViewModel.getWeather("${it.result.latitude},${it.result.longitude}")
            }
    }

    private fun initWeatherListRecyclerView() = with(binding) {
        rvHoursWeather.layoutManager = LinearLayoutManager(activity)
        hoursAdapter = HoursAdapter()
        rvHoursWeather.adapter = hoursAdapter
    }

    private fun updateCards() = with(binding) {
        mainViewModel.weatherLiveData.observe(viewLifecycleOwner) {
            tvLocation.text = it.location.name
            tvTodayTemperature.text = getString(string.today_temperature, it.current.temp_c.toString())
            tvTodayWeatherCondition.text = it.current.condition.text
            Picasso.get().load(getString(string.https) + it.current.condition.icon)
                .into(ivTodayWeatherImage)

            tvTomorrowTemperature.text = getString(
                string.next_days_temperature,
                it.forecast.forecastday[1].day.mintemp_c.toString(),
                it.forecast.forecastday[1].day.maxtemp_c.toString()
            )
            tvTomorrowWeatherCondition.text = it.forecast.forecastday[1].day.condition.text
            Picasso.get().load(getString(string.https) + it.forecast.forecastday[1].day.condition.icon)
                .into(ivTomorrowWeatherImage)

            tvAfterTomorrowTemperature.text = getString(
                string.next_days_temperature,
                it.forecast.forecastday[2].day.mintemp_c.toString(),
                it.forecast.forecastday[2].day.maxtemp_c.toString()
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