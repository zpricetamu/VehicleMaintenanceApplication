package com.example.vehiclemaintenanceapplication.Backend.Repository

import com.example.vehiclemaintenanceapplication.Backend.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.POST

class Repository {

    suspend fun getPost(passw: String): CarDataPost {
        return RetrofitInstance.api.getPost(passw)
    }

    suspend fun getData(datetime: String): Response<CarDataGet> {
        return RetrofitInstance.api.getData(datetime)
    }
    suspend fun getObd(faultcode: String): Response<OBDGet> {
        return RetrofitInstance.api.getObd(faultcode)
    }
    suspend fun pushPost(post: CarUserPost): Response<CarUserPost> {
        return RetrofitInstance.api.pushPost(post)
    }

    suspend fun getFlag(datetime: String): Response<Flagget> {
        return RetrofitInstance.api.getFlag(datetime)
    }
//repository data for all, controls how getxxx works for each
}