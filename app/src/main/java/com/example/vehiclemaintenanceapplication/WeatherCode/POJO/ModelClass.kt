package com.example.vehiclemaintenanceapplication.WeatherCode.POJO
/*
import Main
import Sys
import Wind
*/
import com.google.gson.annotations.SerializedName

data class ModelClass(
//get main stuff from main gson file
    @SerializedName("weather") val weather:List<Weather>,
    @SerializedName("main") val main: Main,
    @SerializedName("wind") val wind: Wind,
    @SerializedName("sys") val sys: Sys,
    @SerializedName("id") val id:Int,
    @SerializedName("name") val name:String


)
