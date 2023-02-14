package com.example.weatherforecast.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherforecast.model.WeatherModel
import com.example.weatherforecast.repository.WeatherRepository

class WeatherViewModel : ViewModel() {

    var weatherLiveData = MutableLiveData<WeatherModel>()

    fun getWeatherRepository(location: String): LiveData<WeatherModel> {
        weatherLiveData = WeatherRepository.getWeatherForecast(location)!!
        return weatherLiveData
    }
}