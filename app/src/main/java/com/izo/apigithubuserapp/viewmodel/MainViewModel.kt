package com.izo.apigithubuserapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.izo.apigithubuserapp.ItemsItem
import com.izo.apigithubuserapp.data.Result
import com.izo.apigithubuserapp.data.UserRepository

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {
    val getSearch: LiveData<Result<List<ItemsItem>>> = userRepository.result

    init {
        findUser("iqbal")
    }

    fun findUser(username: String?) = userRepository.findUser(username)

    fun getThemeSetting() = userRepository.getThemeSetting().asLiveData()
}