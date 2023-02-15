package com.example.weatherforecast.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherforecast.network.WeatherResponse
import com.example.weatherforecast.repository.WeatherRepository

class WeatherViewModel : ViewModel() {

    var weatherLiveData = MutableLiveData<WeatherResponse>()

    fun getWeatherRepository(location: String): LiveData<WeatherResponse> {
        weatherLiveData = WeatherRepository.getWeatherForecast(location)!!
        return weatherLiveData
    }
}