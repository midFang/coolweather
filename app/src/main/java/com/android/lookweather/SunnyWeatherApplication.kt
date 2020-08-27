package com.android.lookweather

import android.app.Application
import android.content.Context

/**
 * Created by midFang on 2020/8/28
 * Useful:
 */
class SunnyWeatherApplication : Application() {

    companion object {
        // 全局提供 context 方式
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

}