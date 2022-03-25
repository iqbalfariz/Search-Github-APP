package com.izo.apigithubuserapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.izo.apigithubuserapp.ItemsItem
import com.izo.apigithubuserapp.UserResponse
import com.izo.apigithubuserapp.data.local.room.FavoriteDao
import com.izo.apigithubuserapp.data.remote.api.ApiConfig
import com.izo.apigithubuserapp.data.remote.api.ApiService
import com.izo.apigithubuserapp.data.remote.response.DetailUserResponse
import com.izo.apigithubuserapp.utils.AppExecutors
import com.izo.apigithubuserapp.viewmodel.DetailViewModel
import com.izo.apigithubuserapp.viewmodel.FollowersViewModel
import com.izo.apigithubuserapp.viewmodel.MainViewModel
import retrofit2.Call
import retrofit2.Response

class UserRepository private constructor(
    private val apiService: ApiService
) {
//    private val result = MediatorLiveData<Result<LiveData<DetailUserResponse>>>()

    // Mengambil data detail user
    fun getDetailUser(username: String?): LiveData<Result<DetailUserResponse>> {
        val result = MutableLiveData<Result<DetailUserResponse>>()
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
                        result.value = Result.Success(responseBody)
                    }
                } else {
                    result.value = Result.Error(response.message().toString())
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                result.value = Result.Error(t.message.toString())
            }

        })
        return result
    }

    // Mengambil user dari key search
    fun findUser(username: String?): LiveData<Result<List<ItemsItem>>> {
        val result = MutableLiveData<Result<List<ItemsItem>>>()
        result.value = Result.Loading
        val client = apiService.getListUser(username)
        client.enqueue(object : retrofit2.Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                // jika koneksi sukses
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        result.value = Result.Success(responseBody?.items)
                    }
                } else {
                    result.value = Result.Error(response.message().toString())
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                // jika koneksi gagal
                result.value = Result.Error(t.message.toString())
            }

        })
        return result
    }

    // Mengambil data follower
    fun getListFollowers(username: String?): LiveData<Result<List<ItemsItem>>> {
        val result = MutableLiveData<Result<List<ItemsItem>>>()
        result.value = Result.Loading
        val client = apiService.getFollowers(username)
        client.enqueue(object : retrofit2.Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                // jika koneksi berhasil
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        result.value = Result.Success(responseBody)
                    } else {
                        result.value = Result.Error(response.message().toString())
                    }
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                result.value = Result.Error(t.message.toString())
            }

        })
        return result
    }

    // Mengambil data following
    fun getListFollowing(username: String?): LiveData<Result<List<ItemsItem>>> {
        val result = MutableLiveData<Result<List<ItemsItem>>>()
        result.value = Result.Loading
        val client = apiService.getFollowing(username)
        client.enqueue(object : retrofit2.Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                // jika koneksi berhasil
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        result.value = Result.Success(responseBody)
                    } else {
                        result.value = Result.Error(response.message().toString())
                    }
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                result.value = Result.Error(t.message.toString())
            }

        })
        return result
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService)
            }.also { instance = it }
    }
}