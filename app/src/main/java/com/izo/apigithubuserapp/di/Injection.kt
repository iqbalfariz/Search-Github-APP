package com.izo.apigithubuserapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.izo.apigithubuserapp.data.ThemePreferences
import com.izo.apigithubuserapp.data.UserRepository
import com.izo.apigithubuserapp.data.local.room.FavoriteDatabase
import com.izo.apigithubuserapp.data.remote.api.ApiConfig
import com.izo.apigithubuserapp.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context, dataStore: DataStore<Preferences>): UserRepository {
        val apiService = ApiConfig.getApiService()
        val database = FavoriteDatabase.getInstance(context)
        val dao = database.favoriteDao()
        val appExecutors = AppExecutors()
        val preferences = ThemePreferences.getInstance(dataStore)
        return UserRepository.getInstance(apiService, dao, appExecutors, preferences)
    }
}