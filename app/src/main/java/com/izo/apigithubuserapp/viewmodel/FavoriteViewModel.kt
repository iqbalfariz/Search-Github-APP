package com.izo.apigithubuserapp.viewmodel

import androidx.lifecycle.ViewModel
import com.izo.apigithubuserapp.data.FavoriteRepository

class FavoriteViewModel(private val favoriteRepository: FavoriteRepository): ViewModel() {
    fun getFavoriteUser() = favoriteRepository.getFavoriteUser()
}