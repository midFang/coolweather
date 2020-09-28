package com.android.lookweather.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.lookweather.logic.Repository
import com.android.lookweather.logic.dao.PlaceDao
import com.android.lookweather.logic.model.Place

/**
 * Created by midFang on 2020/9/4
 * Useful:
 */
class PlaceViewModel : ViewModel() {

    private val searchLiveData = MutableLiveData<String>()

    // 页面上展示的数据,应该都存放在 viewModel 中
    val placeList = ArrayList<Place>()

    // Transformations 是什么意思,为什么要这么做, Transformations 类似监听 searchLiveData 搜索值的改变
    // Transformations 主要用来观察另外一个 liveData 数据发生改变
    val placeLiveData = Transformations.switchMap(searchLiveData) { query ->
        Repository.searchPlaces(query)
    }

    fun searchPlaces(query: String) {
        // 当 query 发生改变的时候,自动触发 Transformations 搜索天气
        searchLiveData.value = query
    }

    fun savePlace(place: Place) = PlaceDao.savePlace(place)

    fun getSavePlace() = PlaceDao.getSavePlace()

    fun isPlaceSaved() = PlaceDao.isPlaceSaved()


}