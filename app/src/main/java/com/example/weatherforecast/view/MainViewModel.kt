package com.example.weatherforecast.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherforecast.model.Weather
import com.example.weatherforecast.model.WeatherService

class MainViewModel : ViewModel() {

    var weatherLiveData = MutableLiveData<Weather>()

    init {
        getWeatherRepository()
    }

    fun getWeatherRepository() {
        weatherLiveData = WeatherService.getWeatherForecast("New York")
    }
}