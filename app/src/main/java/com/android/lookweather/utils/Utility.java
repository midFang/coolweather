package com.android.lookweather.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.lookweather.db.City;
import com.android.lookweather.db.County;
import com.android.lookweather.db.Province;

import android.text.TextUtils;

public class Utility {
	/**
	 * 处理服务器返回的省级的数据
	 */
	public static boolean handlerProvinceResponse(String response){
		if (!TextUtils.isEmpty(response)) {
			try {
				JSONArray allProvince = new JSONArray(response);
				for (int i = 0; i < allProvince.length(); i++) {
					JSONObject provinceObject = allProvince.getJSONObject(i);
					Province province = new Province();
					province.setProvinceCode(provinceObject.getInt("id"));
					province.setProvinceName(provinceObject.getString("name"));
					province.save();
				}
				return true;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	/**
	 * 处理服务器返回的市级的数据
	 */
	public static boolean handlerCityResponse(String response,int provincedId){
		if (!TextUtils.isEmpty(response)) {
			try {
				JSONArray allCities = new JSONArray(response);
				for (int i = 0; i < allCities.length(); i++) {
					JSONObject cityObject = allCities.getJSONObject(i);
					City city = new City();
					city.setCityCode(cityObject.getInt("id"));
					city.setCityName(cityObject.getString("name"));
					// 省级的id在这里设置
					city.setProvinceId(provincedId);
					city.save();
				}
				return true;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	/**
	 * 处理服务器返回的县级的数据
	 */
	public static boolean handlerCountyResponse(String response, int cityId){
		if (!TextUtils.isEmpty(response)) {
			try {
				JSONArray allcountries = new JSONArray(response);
				for (int i = 0; i < allcountries.length(); i++) {
					JSONObject countryObject = allcountries.getJSONObject(i);
					County county = new County();
					county.setCityId(cityId);
					county.setCountyName(countryObject.getString("name"));
					county.setWeatherId(countryObject.getString("weather_id"));
					county.save();
				}
				return true;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
}
