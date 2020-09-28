package com.android.lookweather.ui.place

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.android.lookweather.R
import com.android.lookweather.logic.model.Place
import com.android.lookweather.ui.weather.WeatherActivity
import kotlinx.android.synthetic.main.activity_weather.*

/**
 * Created by midFang on 2020/9/4
 * Useful:
 */
class PlaceAdapter(private val fragment: PlaceFragment, private val placeList: List<Place>) : RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val placeName: TextView = view.findViewById(R.id.placeName)
        val placeAddress: TextView = view.findViewById(R.id.placeAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false)

        val viewHolder = ViewHolder(view)

        viewHolder.itemView.setOnClickListener {
            val place = placeList[viewHolder.adapterPosition]

            val activity = fragment.activity
            if (activity is WeatherActivity) {
                activity.drawerLayout.closeDrawers()
                activity.viewModel.lng = place.location.lng
                activity.viewModel.lat = place.location.lat
                activity.viewModel.placeName = place.name
                activity.refreshWeather()
            } else {
                val intent = Intent(parent.context, WeatherActivity::class.java).apply {
                    putExtra("lng", place.location.lng)
                    putExtra("lat", place.location.lat)
                    putExtra("placeName", place.name)
                }
                parent.context.startActivity(intent)
                activity?.finish()
            }

            fragment.viewModel.savePlace(place)
        }

        return viewHolder
    }

    override fun getItemCount() = placeList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        placeList[position].let {
            holder.placeName.text = it.name
            holder.placeAddress.text = it.address
        }
    }

}
