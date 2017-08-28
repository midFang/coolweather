package com.android.lookweather.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by fangsf on 2017/8/27.
 * 引用 json数据实体类
 */

public class Weather {
    public String status;

    public Basic basic;
    public AQI aqi;
    public Now now;
    public Suggestion suggestion;

    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;
    

}
