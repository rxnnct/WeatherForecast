package com.example.weatherforecast.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecast.model.Weather
import com.example.weatherforecast.model.WeatherService
import com.example.weatherforecast.network.WeatherSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class MainViewModel : ViewModel() {

    var weatherLiveData = MutableLiveData<Weather>()

    fun getWeather(location: String) {
        weatherLiveData = getWeatherFromSource(location)
    }

    fun getWeatherForecast(location: String): MutableLiveData<Weather> {
        return getWeatherFromSource(location)
    }

    private fun getWeatherFromSource(location: String): MutableLiveData<Weather> {
        viewModelScope.launch {
            launch(Dispatchers.IO) {
                val response = WeatherSource().getWeatherForecast(location)
                withContext(Dispatchers.Default)
                {
                    response.let {
                        weatherLiveData.postValue(
                            WeatherService.parseWeatherData(JSONObject(response.body()?.string().toString()))
                        )
                    }
                }
            }
        }
        return weatherLiveData
    }
}