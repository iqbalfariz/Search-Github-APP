package com.izo.apigithubuserapp.di

import android.content.Context
import com.izo.apigithubuserapp.data.FavoriteRepository
import com.izo.apigithubuserapp.data.local.room.FavoriteDatabase
import com.izo.apigithubuserapp.data.remote.api.ApiConfig
import com.izo.apigithubuserapp.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): FavoriteRepository {
        val apiService = ApiConfig.getApiService()
        val database = FavoriteDatabase.getInstance(context)
        val dao = database.favoriteDao()
        val appExecutors = AppExecutors()
        return FavoriteRepository.getInstance(apiService, dao, appExecutors)
    }
}