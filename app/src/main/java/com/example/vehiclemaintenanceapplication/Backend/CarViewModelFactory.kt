package com.example.vehiclemaintenanceapplication.Backend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vehiclemaintenanceapplication.Backend.Repository.Repository

class CarViewModelFactory(
    private val repository: Repository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CarViewModel(repository) as T
    }
}