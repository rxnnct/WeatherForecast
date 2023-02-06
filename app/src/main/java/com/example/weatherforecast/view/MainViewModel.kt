package com.example.weatherforecast.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherforecast.model.Weather

class MainViewModel : ViewModel() {
    val weatherLiveData = MutableLiveData<Weather>()
}