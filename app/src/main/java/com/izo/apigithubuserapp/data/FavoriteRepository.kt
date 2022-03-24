package com.izo.apigithubuserapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.izo.apigithubuserapp.UserResponse
import com.izo.apigithubuserapp.data.local.entity.FavoriteEntity
import com.izo.apigithubuserapp.data.local.room.FavoriteDao
import com.izo.apigithubuserapp.data.remote.api.ApiService
import com.izo.apigithubuserapp.data.remote.response.DetailUserResponse
import com.izo.apigithubuserapp.utils.AppExecutors
import com.izo.apigithubuserapp.viewmodel.DetailViewModel
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class FavoriteRepository private constructor(
    private val apiService: ApiService,
    private val favoriteDao: FavoriteDao,
    private val appExecutors: AppExecutors
){
    private val result = MediatorLiveData<Result<List<FavoriteEntity>>>()

    var username: String? = "username"

    fun getFavoriteUser(): LiveData<Result<List<FavoriteEntity>>> {
        result.value = Result.Loading
        val client = apiService.getUser(username)
        client.enqueue(object : retrofit2.Callback<DetailUserResponse>{
            // jika koneksi berhasil
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                if(response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        var items = FavoriteEntity()
                        appExecutors.diskIO.execute {
                            items = FavoriteEntity (
                                responseBody.id,
                                responseBody.login,
                                responseBody.avatarUrl,
                                responseBody.url
                                    )
                        }
                        favoriteDao.insertFavorite(items)

                    }
                } else {
                    result.value = Result.Error(response.message().toString())
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                result.value = Result.Error(t.message.toString())
            }

        })
        val localData = favoriteDao.getFavorite()
        result.addSource(localData) { newData: List<FavoriteEntity> ->
            result.value = Result.Success(newData)
        }
        return result
    }

    companion object {
        @Volatile
        private var instance: FavoriteRepository? = null
        fun getInstance(
            apiService: ApiService,
            newsDao: FavoriteDao,
            appExecutors: AppExecutors
        ): FavoriteRepository =
            instance ?: synchronized(this) {
                instance ?: FavoriteRepository(apiService, newsDao, appExecutors)
            }.also { instance = it }
    }


}