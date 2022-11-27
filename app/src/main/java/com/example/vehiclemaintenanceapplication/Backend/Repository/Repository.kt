package com.example.vehiclemaintenanceapplication.Backend.Repository

import com.example.vehiclemaintenanceapplication.Backend.*
import retrofit2.Response
import retrofit2.http.POST

class Repository {

    suspend fun getPost(passw: String): CarDataPost {
        return RetrofitInstance.api.getPost(passw)
    }

    suspend fun getData(): CarDataGet {
        return RetrofitInstance.api.getData()
    }
    suspend fun getObd(): OBDGet {
        return RetrofitInstance.api.getObd()
    }

    suspend fun pushPost(post: CarUserPost): Response<CarUserPost> {
        return RetrofitInstance.api.pushPost(post)
    }

}