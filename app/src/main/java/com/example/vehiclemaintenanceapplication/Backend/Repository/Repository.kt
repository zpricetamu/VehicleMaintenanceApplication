package com.example.vehiclemaintenanceapplication.Backend.Repository

import com.example.vehiclemaintenanceapplication.Backend.CarDataGet
import com.example.vehiclemaintenanceapplication.Backend.CarDataPost
import com.example.vehiclemaintenanceapplication.Backend.OBDGet
import com.example.vehiclemaintenanceapplication.Backend.RetrofitInstance
import retrofit2.Response

class Repository {

    suspend fun getPost(): CarDataPost {
        return RetrofitInstance.api.getPost()
    }

    suspend fun getData(): CarDataGet {
        return RetrofitInstance.api.getData()
    }
    suspend fun getObd(): OBDGet {
        return RetrofitInstance.api.getObd()
    }
}