package com.izo.apigithubuserapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.izo.apigithubuserapp.ItemsItem
import com.izo.apigithubuserapp.UserResponse
import com.izo.apigithubuserapp.data.UserRepository
import com.izo.apigithubuserapp.data.remote.api.ApiConfig
import retrofit2.Call
import retrofit2.Response

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {
//    init {
//        findUser("iqbal")
//    }

    fun findUser(username: String?) = userRepository.findUser(username)

}