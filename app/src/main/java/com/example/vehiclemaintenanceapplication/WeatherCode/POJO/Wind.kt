package com.example.vehiclemaintenanceapplication.WeatherCode.POJO

import com.google.gson.annotations.SerializedName

data class Wind(
//wind info from gson file
    @SerializedName("speed") val speed : Double,
    @SerializedName("deg") val deg : Int
)
