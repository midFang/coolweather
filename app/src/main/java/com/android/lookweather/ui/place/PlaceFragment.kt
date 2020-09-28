package com.android.lookweather.ui.place

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.lookweather.MainActivity
import com.android.lookweather.R
import com.android.lookweather.ui.weather.WeatherActivity
import kotlinx.android.synthetic.main.fragment_place.*

/**
 * Created by midFang on 2020/9/4
 * Useful:
 */
class PlaceFragment : Fragment() {

    val viewModel by lazy { ViewModelProviders.of(this).get(PlaceViewModel::class.java) }

    private lateinit var adapter: PlaceAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_place, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (activity is MainActivity && viewModel.isPlaceSaved()) {
            val place = viewModel.getSavePlace()
            val intent = Intent(context, WeatherActivity::class.java).apply {
                putExtra("lng", place.location.lng)
                putExtra("lat", place.location.lat)
                putExtra("placeName", place.name)
            }
            startActivity(intent)
            activity?.finish()
            return
        }

        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        adapter = PlaceAdapter(this, viewModel.placeList)
        recyclerView.adapter = adapter

        searchPlaceEdit.addTextChangedListener {
            val content = it.toString()

            if (content.isNotEmpty()) {
                // 搜索
                viewModel.searchPlaces(content)
            } else {
                // 清除数据
                recyclerView.visibility = View.GONE
                bgImageView.visibility = View.GONE
                viewModel.placeList.clear()
                adapter.notifyDataSetChanged()
            }
        }


        viewModel.placeLiveData.observe(this, Observer { result ->
            val places = result.getOrNull()
            places?.let {
                recyclerView.visibility = View.VISIBLE
                bgImageView.visibility = View.VISIBLE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(it)
                adapter.notifyDataSetChanged()
            } ?: let {
                Toast.makeText(activity, "not places", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })


    }


}

