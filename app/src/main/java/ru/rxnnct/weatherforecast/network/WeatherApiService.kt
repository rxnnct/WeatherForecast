package ru.rxnnct.weatherforecast.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.rxnnct.weatherforecast.model.Weather

interface WeatherApiService {

    //Maybe Map is better
    @GET("forecast.json")
    suspend fun getWeatherForecast(
        @Query("key") key: String,
        @Query("q") q: String,
        @Query("days") days: String,
        @Query("aqi") aqi: String,
        @Query("alerts") alerts: String,
    ): Response<Weather>
}