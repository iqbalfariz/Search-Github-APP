package com.izo.apigithubuserapp.viewmodel

import androidx.lifecycle.ViewModel
import com.izo.apigithubuserapp.data.UserRepository

class FollowingViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getListFollowing(username: String?) = userRepository.getListFollowing(username)
}