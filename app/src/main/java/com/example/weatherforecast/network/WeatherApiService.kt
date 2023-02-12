package com.example.weatherforecast.network

import retrofit2.Response
import retrofit2.http.GET

interface WeatherApiService {

    // TODO: params
    @GET("forecast.json?key=92ebf3024f0c4d17a7f143512232901&q=London&days=1&aqi=no&alerts=no")
    suspend fun getWeatherData(): Response<WeatherRequestData>
}