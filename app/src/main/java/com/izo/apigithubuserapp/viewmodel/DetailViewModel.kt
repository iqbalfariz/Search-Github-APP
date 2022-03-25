package com.izo.apigithubuserapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.izo.apigithubuserapp.data.UserRepository
import com.izo.apigithubuserapp.data.remote.api.ApiConfig
import com.izo.apigithubuserapp.data.remote.response.DetailUserResponse
import retrofit2.Call
import retrofit2.Response

class DetailViewModel(private val userRepository: UserRepository): ViewModel() {
    fun getDetailUser(username: String?) = userRepository.getDetailUser(username)
}