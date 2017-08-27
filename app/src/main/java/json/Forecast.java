package json;

import com.google.gson.annotations.SerializedName;

/**
 * Created by fangsf on 2017/8/27.
 * 未来的天气信息
 */

public class Forecast {
    public String date;

    @SerializedName("tmp")
    public Temperature temperature;

    @SerializedName("cond")
    public More more;

    public class Temperature {
        public String max;
        public String min;
    }

    public class More {
        @SerializedName("txt_d")
        public String info;
    }

}
