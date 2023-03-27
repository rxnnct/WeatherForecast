package ru.rxnnct.weatherforecast.network

import ru.rxnnct.weatherforecast.BuildConfig
import retrofit2.Response
import ru.rxnnct.weatherforecast.model.Weather

class WeatherRemoteSource {
    private val retrofitService = RetrofitClient.retrofitService

    suspend fun getWeatherForecast(location: String): Response<Weather> {
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