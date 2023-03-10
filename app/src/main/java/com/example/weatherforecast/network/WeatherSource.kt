package com.example.weatherforecast.network

import okhttp3.ResponseBody
import retrofit2.Response

class WeatherSource {
    private val retrofitService = RetrofitClient.retrofitService

    suspend fun getWeatherForecast(location: String): Response<ResponseBody> {
        return retrofitService.getWeatherForecast(
            KEY,
            location,
            DAYS,
            AQI,
            ALERTS
        )
    }

    companion object {
        private const val KEY = "92ebf3024f0c4d17a7f143512232901"
        private const val DAYS = "3"
        private const val AQI = "no"
        private const val ALERTS = "no"
    }
}