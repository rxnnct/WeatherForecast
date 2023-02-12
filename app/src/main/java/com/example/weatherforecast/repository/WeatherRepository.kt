package com.example.weatherforecast.repository

import androidx.lifecycle.MutableLiveData
import com.example.weatherforecast.network.RetrofitWeatherSource
import com.example.weatherforecast.model.WeatherData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherRepository {
    companion object {
        fun getWeatherForecast(location: String): MutableLiveData<WeatherData>? {

            val weatherLiveData: MutableLiveData<WeatherData> = MutableLiveData<WeatherData>()

            CoroutineScope(Dispatchers.Default).launch {

                launch(Dispatchers.IO) {
                    val response = RetrofitWeatherSource().getWeatherForecast(location)
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