package com.example.vehiclemaintenanceapplication.Backend

import com.example.vehiclemaintenanceapplication.Backend.util.Constant.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val api: APICar by lazy {
        retrofit.create(APICar::class.java)
    }

}