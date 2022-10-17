package com.example.vehiclemaintenanceapplication.Backend

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vehiclemaintenanceapplication.Backend.Repository.Repository
import kotlinx.coroutines.launch

class CarViewModel(private val repository: Repository): ViewModel() {

    val myResponse: MutableLiveData<CarDataPost> = MutableLiveData()
    val myResponse2: MutableLiveData<CarDataGet> = MutableLiveData()
    val myResponse3: MutableLiveData<OBDGet> = MutableLiveData()

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

}