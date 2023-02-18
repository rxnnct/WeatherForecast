package com.example.weatherforecast.model

import androidx.lifecycle.MutableLiveData
import com.example.weatherforecast.network.WeatherApiService
import com.example.weatherforecast.network.WeatherSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class WeatherService {

    companion object {
        fun getWeatherForecast(location: String): MutableLiveData<Weather> {
            return getWeatherFromSource(location)
        }

        fun getWeatherForecast(): MutableLiveData<Weather> {
            //getLocation
            val location = "Minsk"
            return getWeatherFromSource(location)
        }

        private fun getWeatherFromSource(location: String): MutableLiveData<Weather> {

            val weatherLiveData: MutableLiveData<Weather> = MutableLiveData<Weather>()

            CoroutineScope(Dispatchers.Default).launch {
                launch(Dispatchers.IO) {
                    val response = WeatherSource().getWeatherForecast(location)
                    withContext(Dispatchers.Default)
                    {
                        response.let {
                            weatherLiveData.postValue(
                                parseWeatherData(JSONObject(response.body()?.string().toString()))
                            )
                        }
                    }
                }
            }
            return weatherLiveData
        }

        private fun parseWeatherData(weatherJsonObject: JSONObject): Weather {
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