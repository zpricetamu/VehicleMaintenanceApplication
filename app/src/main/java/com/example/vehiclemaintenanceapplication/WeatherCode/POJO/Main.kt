package com.example.vehiclemaintenanceapplication.WeatherCode.POJO

import com.google.gson.annotations.SerializedName

//get more info from gson file that fits this stuff
data class Main(
    @SerializedName("temp") val temp : Double,
    @SerializedName("feels_like") val feels_like : Double,
    @SerializedName("temp_min") val temp_min : Double,
    @SerializedName("temp_max") val temp_max : Double,
    @SerializedName("pressure") val pressure : Int,
    @SerializedName("humidity") val humidity : Int

)
