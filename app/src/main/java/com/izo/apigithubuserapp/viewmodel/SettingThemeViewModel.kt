package com.izo.apigithubuserapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.izo.apigithubuserapp.data.UserRepository
import kotlinx.coroutines.launch

class SettingThemeViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getThemeSetting() = userRepository.getThemeSetting().asLiveData()

    fun saveThemeSetting(darkModeState: Boolean) {
        viewModelScope.launch {
            userRepository.saveThemeSetting(darkModeState)
        }
    }

}