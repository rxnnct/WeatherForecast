package com.example.weatherforecast.view

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherforecast.model.Weather
import com.example.weatherforecast.model.WeatherService

class MainViewModel : ViewModel() {

    var weatherLiveData = MutableLiveData<Weather>()

    init {
        getWeather()
    }

    fun getWeather() {
        weatherLiveData = WeatherService.getWeatherForecast()
    }

    fun getWeather(location: String) {
        weatherLiveData = WeatherService.getWeatherForecast(location)
        Log.d("MyLog", weatherLiveData.value?.location.toString())
    }
}