package com.example.vehiclemaintenanceapplication.Backend

import com.google.gson.annotations.SerializedName


data class OBDGet (
    @SerializedName("code") val code : String,
    @SerializedName("description") val descrip : String,
    @SerializedName("issues") val issues : String,
    @SerializedName("name") val partname : String,
    @SerializedName("solutions") val solution : String,

        )
