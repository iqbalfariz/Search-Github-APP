package com.izo.apigithubuserapp.viewmodel

import androidx.lifecycle.ViewModel
import com.izo.apigithubuserapp.data.UserRepository

class FollowersViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getListFollowers(username: String?) = userRepository.getListFollowers(username)
}