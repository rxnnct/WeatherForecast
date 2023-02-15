package com.example.weatherforecast.model

data class Weather(
    val location: String,
    val temperature: String,
    val condition: Condition,
    val forecastDays: ArrayList<ForecastDay>
)

data class ForecastDay(
    val maxTemperature: String,
    val minTemperature: String,
    val condition: Condition,
    val forecastHours: ArrayList<ForecastHour>
)

data class Condition(
    val description: String,
    val imageUrl: String
)

data class ForecastHour(
    val hour: String,
    val condition: Condition,
    val temperature: String
)
