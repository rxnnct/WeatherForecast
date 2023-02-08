package com.example.weatherforecast.model

data class Weather(
    val location: String,
    val currentTemperature: String,
    val condition: Condition,
    val forecast: ArrayList<DayForecast>
)

data class DayForecast(
    val date: String,
    val maxTemperature: String,
    val minTemperature: String,
    val condition: Condition,
    val hoursForecast: ArrayList<HourForecast>
)

data class HourForecast(
    val time: String,
    val temperature: String,
    val condition: Condition
)

data class Condition(
    val description: String,
    val iconUrl: String
)