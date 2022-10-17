package com.example.vehiclemaintenanceapplication.Backend

import retrofit2.http.GET

interface APICar {

    @GET("uservalue/testing")
    suspend fun getPost(): CarDataPost

    @GET("sensorvalue/2022-09-28T07:01:40")
    suspend fun getData(): CarDataGet

    @GET("obdcode/P0420")
    suspend fun getObd(): OBDGet

}