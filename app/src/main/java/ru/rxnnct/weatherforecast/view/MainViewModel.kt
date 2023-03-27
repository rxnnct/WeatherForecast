package ru.rxnnct.weatherforecast.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.rxnnct.weatherforecast.network.WeatherRemoteSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.rxnnct.weatherforecast.model.Weather

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
                val response = WeatherRemoteSource().getWeatherForecast(location)
                withContext(Dispatchers.Default)
                {
                    response.let {
                        weatherLiveData.postValue(response.body())
                    }
                }
            }
        }
        return weatherLiveData
    }
}