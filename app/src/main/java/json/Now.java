package json;

import com.google.gson.annotations.SerializedName;

/**
 * Created by fangsf on 2017/8/27.
 * Now 现在的天气的状况
 */

public class Now {

    @SerializedName("tmp")
    public String temprature;

    @SerializedName("cond")
    public More more;

    public class More {

        @SerializedName("txt")
        public String info;
    }

}
