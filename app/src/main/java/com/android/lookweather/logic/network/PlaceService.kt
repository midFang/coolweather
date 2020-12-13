package com.android.lookweather.logic.network

import com.android.lookweather.SunnyWeatherApplication
import com.android.lookweather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by midFang on 2020/8/28
 * Useful:
 */
interface PlaceService {

    /**
     *  todo 为什么直接返回了一个 call 请求
     */
    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>

}