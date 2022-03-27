package com.izo.apigithubuserapp.viewmodel

import androidx.lifecycle.ViewModel
import com.izo.apigithubuserapp.data.UserRepository
import com.izo.apigithubuserapp.data.local.entity.FavoriteEntity

class DetailViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getDetailUser(username: String?) = userRepository.getDetailUser(username)

    fun insertData(favoriteUser: FavoriteEntity) = userRepository.insertData(favoriteUser)

    fun isFavorite(userId: Int) = userRepository.isFavorite(userId)

    fun deleteData(favoriteEntity: FavoriteEntity) = userRepository.deleteData(favoriteEntity)
}