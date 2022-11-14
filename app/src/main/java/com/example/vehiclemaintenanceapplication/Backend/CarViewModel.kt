package com.example.vehiclemaintenanceapplication.Backend

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vehiclemaintenanceapplication.Backend.Repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.http.POST

class CarViewModel(private val repository: Repository): ViewModel() {

    val myResponse: MutableLiveData<CarDataPost> = MutableLiveData()
    val myResponse2: MutableLiveData<CarDataGet> = MutableLiveData()
    val myResponse3: MutableLiveData<OBDGet> = MutableLiveData()
    val myResponse4: MutableLiveData<Response<CarUserPost>> = MutableLiveData()


    fun getPost() {
        viewModelScope.launch {
            val response: CarDataPost = repository.getPost()
            myResponse.value=response
        }
    }

    fun getData() {
        viewModelScope.launch {
            val response2: CarDataGet = repository.getData()
            myResponse2.value=response2
        }

    }

    fun getObd() {
        viewModelScope.launch {
            val response3: OBDGet = repository.getObd()
            myResponse3.value=response3

        }
    }

    fun pushPost(post: CarUserPost) {
        viewModelScope.launch {
            val response4 = repository.pushPost(post)
            myResponse4.value = response4
        }
    }


}