package com.example.weatherforecast.repository

import androidx.lifecycle.MutableLiveData
import com.example.weatherforecast.network.WeatherSource
import com.example.weatherforecast.model.WeatherModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherRepository {
    companion object {
        fun getWeatherForecast(location: String): MutableLiveData<WeatherModel>? {

            val weatherLiveData: MutableLiveData<WeatherModel> = MutableLiveData<WeatherModel>()

            CoroutineScope(Dispatchers.Default).launch {

                launch(Dispatchers.IO) {
                    val response = WeatherSource().getWeatherForecast(location)
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
}