package com.example.weatherforecast.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherforecast.model.Weather
import com.example.weatherforecast.repository.WeatherRepository

class WeatherViewModel : ViewModel() {

    var weatherLiveData = MutableLiveData<Weather>()

    fun getWeatherRepository(location: String): LiveData<Weather> {
        weatherLiveData = WeatherRepository.getWeatherForecast(location)
        return weatherLiveData
    }
}