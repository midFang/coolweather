package com.android.lookweather.logic

import android.content.Context
import androidx.lifecycle.liveData
import com.android.lookweather.logic.dao.PlaceDao
import com.android.lookweather.logic.model.Place
import com.android.lookweather.logic.model.PlaceResponse
import com.android.lookweather.logic.model.Weather
import com.android.lookweather.logic.network.SunnyWeatherNetWork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext

/**
 * Created by midFang on 2020/9/3
 * Useful: 仓库层
 */
object Repository {

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) = liveData<Result<T>>(context) {
        val result = try {
            block()
        } catch (e: Exception) {
            Result.failure<T>(e)
        }
        emit(result)
    }


    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
        coroutineScope {
            val deferredRealtime = async {
                SunnyWeatherNetWork.getRealtimeWeather(lng, lat)
            }
            // 暂缓获取数据
            val deferredDaily = async {
                SunnyWeatherNetWork.getDailyWeather(lng, lat)
            }

            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()

            if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                val weather = Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                Result.success(weather)
            } else {
                Result.failure(RuntimeException("refreshWeather is error realtimeResponse ${realtimeResponse.status} dailyResponse ${dailyResponse.status} "))
            }
        }
    }


    // 是一个 liveData 函数, 可以挂载
    fun searchPlaces(query: String) = fire(Dispatchers.IO) {

        val placeResponse = SunnyWeatherNetWork.searchPlaces(query)
        if (placeResponse.status == "ok") {
            val places = placeResponse.places
            // 包装了一个结构 LiveData<Result<List<Place>>>
            Result.success(places)
        } else {
            Result.failure(RuntimeException("response status is error"))
        }

//        // 无法直接返回 liveData 对象,所以使用发射的方法
//        emit(result)
    }

}