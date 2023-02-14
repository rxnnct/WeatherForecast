package com.example.weatherforecast.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherforecast.model.WeatherData
import com.example.weatherforecast.repository.WeatherRepository

class WeatherViewModel : ViewModel() {

    var weatherLiveData = MutableLiveData<WeatherData>()

    fun getWeatherRepository(location: String): LiveData<WeatherData> {
        weatherLiveData = WeatherRepository.getWeatherForecast(location)!!
        return weatherLiveData
    }
//    init {
//        weatherLiveData = WeatherRepository.getWeatherForecast("London")!!
//    }
}