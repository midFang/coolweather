package com.android.lookweather.json;

/**
 * Created by fangsf on 2017/8/27.
 * 空气质量指数
 */

public class AQI {

    public AQICity city;

    public class AQICity {

        public String aqi;

        public String pm25;
    }

}
