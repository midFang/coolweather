package com.android.lookweather.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.android.lookweather.SunnyWeatherApplication
import com.android.lookweather.logic.model.Place
import com.google.gson.Gson

/**
 * Created by midFang on 2020/9/13
 * Useful: 数据层
 */
object PlaceDao {

    private fun sharePreference() = SunnyWeatherApplication.context.getSharedPreferences("sunny_weather", Context.MODE_PRIVATE)

    fun savePlace(place: Place) {
        sharePreference().edit {
            putString("place", Gson().toJson(place))
        }
    }

    fun getSavePlace(): Place {
        val json = sharePreference().getString("place", "")
        return Gson().fromJson<Place>(json, Place::class.java)
    }

    fun isPlaceSaved() = sharePreference().contains("place")

}