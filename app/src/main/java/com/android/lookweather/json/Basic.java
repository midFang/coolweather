package com.android.lookweather.json;

import com.google.gson.annotations.SerializedName;

/**
 * Created by fangsf on 2017/8/27.
 */

public class Basic {
    @SerializedName("city")
    public String cityName;

    @SerializedName("id")
    public String weatherId;

    public Update update;

    public class Update {
        @SerializedName("loc")
        public String updateTime;
    }

}
