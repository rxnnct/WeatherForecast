package com.example.weatherforecast.model

data class Weather(
    val location: String,
    val lastUpdated: String,
    val currentTemperature: String,
    val condition: Condition,
    val forecast: List<DayForecast>
)

data class DayForecast(
    val date: String,
    val maxTemperature: String,
    val minTemperature: String,
    val condition: Condition,
    val hoursForecast: List<HourForecast>
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