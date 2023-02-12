package com.example.weatherforecast.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherforecast.model.WeatherData
import com.example.weatherforecast.repository.WeatherRepository

class WeatherViewModel : ViewModel() {

    private var weatherLiveData = MutableLiveData<WeatherData>()
    fun getCustomersRepository(location: String): LiveData<WeatherData> {
        return weatherLiveData
    }
    init {
        weatherLiveData = WeatherRepository.getWeatherForecast("London")!!
    }
}