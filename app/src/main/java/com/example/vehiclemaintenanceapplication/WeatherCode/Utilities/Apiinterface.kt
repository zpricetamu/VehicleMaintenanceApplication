package com.example.vehiclemaintenanceapplication.WeatherCode.Utilities

import com.example.vehiclemaintenanceapplication.Backend.CarDataGet
import com.example.vehiclemaintenanceapplication.WeatherCode.POJO.ModelClass
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Apiinterface {
//gets values from api from first file
    @GET("weather")
    fun getCurrentWeatherdata(
        @Query("lat") latitude:String,
        @Query("lon") longitude:String,
        @Query("APPID") api_key:String
    ):Call<ModelClass>

    @GET("weather")
    fun getCityWeatherData(
    @Query("q") cityName:String,
    @Query("APPID") api_key:String
    ):Call<ModelClass>


}