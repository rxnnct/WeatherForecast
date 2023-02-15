package com.example.weatherforecast.network

data class WeatherResponse(
    val current: Current,
    val forecast: Forecast,
    val location: Location
) {
    data class Current(
        val condition: Condition,
        val temp_c: Double
    )

    data class Forecast(
        val forecastday: List<Forecastday>
    ) {
        data class Forecastday(
            val day: Day,
            val hour: List<Hour>
        ) {

            data class Day(
                val condition: Condition,
                val maxtemp_c: Double,
                val mintemp_c: Double
            )

            data class Hour(
                val condition: Condition,
                val temp_c: Double,
                val time: String
            )
        }
    }

    data class Location(
        val name: String
    )

    data class Condition(
        val icon: String,
        val text: String
    )
}
