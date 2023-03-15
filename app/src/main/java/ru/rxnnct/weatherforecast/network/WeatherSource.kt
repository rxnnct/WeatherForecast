package ru.rxnnct.weatherforecast.network

import ru.rxnnct.weatherforecast.BuildConfig
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
        private const val KEY = BuildConfig.WEATHER_API_KEY
        private const val DAYS = "3"
        private const val AQI = "no"
        private const val ALERTS = "no"
    }
}