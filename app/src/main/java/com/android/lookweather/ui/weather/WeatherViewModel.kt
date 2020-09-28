package com.android.lookweather.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.android.lookweather.logic.Repository
import com.android.lookweather.logic.model.Location

/**
 * Created by midFang on 2020/9/5
 * Useful:
 */
class WeatherViewModel : ViewModel() {

    private val locationLiveData = MutableLiveData<Location>()

    var lng = ""

    var lat = ""

    var placeName = ""


    // 观察 locationLiveData 的变化
    val weatherLiveData = Transformations.switchMap(locationLiveData) {
        Repository.refreshWeather(it.lng, it.lat)
    }

    fun refreshWeather(lng: String, lat: String) {
        locationLiveData.value = Location(lng, lat)
    }

}