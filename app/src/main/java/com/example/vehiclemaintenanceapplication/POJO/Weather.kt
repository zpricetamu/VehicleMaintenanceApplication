package com.example.vehiclemaintenanceapplication.POJO

import com.google.gson.annotations.SerializedName
//weather data from gson file
data class Weather(
    @SerializedName("id") val id:Int,
    @SerializedName("main") val main:String,
    @SerializedName("description") val description:String,
    @SerializedName("icon") val icon:String

)
