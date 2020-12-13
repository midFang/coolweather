package com.android.lookweather.logic.network

import android.app.DownloadManager
import com.android.lookweather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Created by midFang on 2020/9/3
 * Useful:
 */
object SunnyWeatherNetWork {

    // 使用的是泛型实化
    private val placeService = ServiceCreator.create<PlaceService>()
    // 和上面的代码是一样的效果, 只不过上面的代码不需要传递参数
    //  private val placeService = ServiceCreator.create(PlaceService::class.java)

    private val weatherService = ServiceCreator.create(WeatherService::class.java)

    suspend fun getDailyWeather(lng: String, lat: String) = weatherService.getDailyWeather(lng, lat).await()

    suspend fun getRealtimeWeather(lng: String, lat: String) = weatherService.getRealtimeWeather(lng, lat).await()


    // retrofit 内部已经实现了一个  await 方法
    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()


    // 进行了重写
    private suspend fun <T> Call<T>.await(): T {
        // suspendCoroutine 有什么特点,和区别
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) {
                        continuation.resume(body)
                    } else {
                        continuation.resumeWithException(RuntimeException("response body is null"))
                    }
                }
            })
        }
    }

}