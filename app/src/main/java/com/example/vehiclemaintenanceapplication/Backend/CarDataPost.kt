package com.example.vehiclemaintenanceapplication.Backend

import com.google.gson.annotations.SerializedName
//car user data to pull from server
data class CarDataPost (
    @SerializedName("name") val name : String?,
    @SerializedName("user_email") val user_email : String?,
    @SerializedName("user_password") val user_password : String?,
    @SerializedName("user_vehicle_make") val user_vehicle_make : String?,
    @SerializedName("user_vehicle_model") val user_vehicle_model : String?,
    @SerializedName("user_vehicle_year") val user_vehicle_year : String?,

        )