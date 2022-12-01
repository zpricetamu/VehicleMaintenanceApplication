package com.example.vehiclemaintenanceapplication.Backend

import com.google.firebase.auth.UserInfo
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface APICar {

    @GET("uservalue/{password}")
    suspend fun getPost(
        @Path("password") string: String
    ): CarDataPost

    @GET("sensorvalue/{datetime}")
    suspend fun getData(
        @Path("datetime") string: String
    ): Response<CarDataGet>

    @GET("obdcode/{faultcode}")
    suspend fun getObd(
        @Path("faultcode") string: String
    ): Response<OBDGet>

    @POST("uservalue")
    suspend fun pushPost(
        @Body post: CarUserPost
    ): Response<CarUserPost>

    @GET("flag/{datetime}")
    suspend fun getFlag(
        @Path("datetime") string: String
    ): Response<Flagget>

}