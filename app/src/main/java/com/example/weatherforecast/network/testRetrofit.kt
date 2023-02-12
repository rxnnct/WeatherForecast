package com.example.weatherforecast.network

// TODO: remove
suspend fun main() {
    println(WeatherApi.retrofitService.getWeatherData().body()?.current?.temp_c)
}