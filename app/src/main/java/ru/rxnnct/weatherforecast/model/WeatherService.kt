package ru.rxnnct.weatherforecast.model

import org.json.JSONObject

class WeatherService {

    companion object {

        fun parseWeatherData(weatherJsonObject: JSONObject): Weather {
            val daysList = ArrayList<ForecastDay>()
            val daysArray = weatherJsonObject.getJSONObject("forecast").getJSONArray("forecastday")
            for (i in 0 until daysArray.length()) {
                val day = daysArray[i] as JSONObject

                val hoursList = ArrayList<ForecastHour>()
                val hoursArray = day.getJSONArray("hour")
                for (j in 0 until hoursArray.length()) {
                    val hour = hoursArray[j] as JSONObject
                    val hourItem = ForecastHour(
                        hour.getString("time"),
                        Condition(
                            hour.getJSONObject("condition")
                                .getString("text"),
                            hour.getJSONObject("condition")
                                .getString("icon")
                        ),
                        hour.getString("temp_c"),
                    )
                    hoursList.add(hourItem)
                }

                val dayItem = ForecastDay(
                    day.getJSONObject("day").getString("maxtemp_c").toFloat().toInt().toString(),
                    day.getJSONObject("day").getString("mintemp_c").toFloat().toInt().toString(),
                    Condition(
                        day.getJSONObject("day").getJSONObject("condition").getString("text"),
                        day.getJSONObject("day").getJSONObject("condition").getString("icon"),
                    ),
                    hoursList
                )
                daysList.add(dayItem)
            }

            return Weather(
                weatherJsonObject.getJSONObject("location").getString("name"),
                weatherJsonObject.getJSONObject("current").getString("temp_c"),
                Condition(
                    weatherJsonObject.getJSONObject("current")
                        .getJSONObject("condition").getString("text"),
                    weatherJsonObject.getJSONObject("current")
                        .getJSONObject("condition").getString("icon"),
                ),
                daysList
            )
        }
    }
}