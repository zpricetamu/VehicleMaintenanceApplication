package com.example.vehiclemaintenanceapplication.Backend

import com.google.firebase.auth.UserInfo
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface APICar {

    @GET("uservalue/Test103122")
    suspend fun getPost(): CarDataPost

    @GET("sensorvalue/2022-09-28T07:01:40")
    suspend fun getData(): CarDataGet

    @GET("obdcode/P0420")
    suspend fun getObd(): OBDGet

    //added these three lines
    @POST("uservalue/testpassword")
    suspend fun pushPost(
        @Body post: CarUserPost
    ): Response<CarUserPost>

}