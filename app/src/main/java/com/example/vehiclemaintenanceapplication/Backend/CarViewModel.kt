package com.example.vehiclemaintenanceapplication.Backend

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vehiclemaintenanceapplication.Backend.Repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.http.POST

class CarViewModel(private val repository: Repository): ViewModel() {

    var myResponse: MutableLiveData<CarDataPost> = MutableLiveData()
    var myResponse2: MutableLiveData<Response<CarDataGet>> = MutableLiveData()
    var myResponse3: MutableLiveData<Response<OBDGet>> = MutableLiveData()
    var myResponse4: MutableLiveData<Response<CarUserPost>> = MutableLiveData()
    var myResponse5: MutableLiveData<Response<Flagget>> = MutableLiveData()
        // m


    fun getPost(passw: String) {
        viewModelScope.launch {
            val response: CarDataPost = repository.getPost(passw)
            myResponse.value=response
        }
    }

    fun getData(datetime: String) {
        viewModelScope.launch {
            val response2: Response<CarDataGet> = repository.getData(datetime)
            myResponse2.value=response2
        }

    }

    fun getObd(faultcode: String) {
        viewModelScope.launch {
            val response3: Response<OBDGet> = repository.getObd(faultcode)
            myResponse3.value=response3

        }
    }

    fun pushPost(post: CarUserPost) {
        viewModelScope.launch {
            val response4 = repository.pushPost(post)
            myResponse4.value = response4
        }
    }

    fun getFlag(datetime: String) {
        viewModelScope.launch {
            val response5: Response<Flagget> = repository.getFlag(datetime)
            myResponse5.value=response5
        }
    }

}