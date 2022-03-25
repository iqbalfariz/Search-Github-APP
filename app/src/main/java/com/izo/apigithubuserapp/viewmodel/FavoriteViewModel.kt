package com.izo.apigithubuserapp.viewmodel

import androidx.lifecycle.ViewModel
import com.izo.apigithubuserapp.data.UserRepository
import com.izo.apigithubuserapp.data.local.entity.FavoriteEntity

class FavoriteViewModel(private val userRepository: UserRepository): ViewModel() {

    fun getData() = userRepository.getData()

    fun deleteData(favoriteEntity: FavoriteEntity) = userRepository.deleteData(favoriteEntity)
}