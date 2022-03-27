package com.izo.apigithubuserapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.izo.apigithubuserapp.ItemsItem
import com.izo.apigithubuserapp.UserResponse
import com.izo.apigithubuserapp.data.local.entity.FavoriteEntity
import com.izo.apigithubuserapp.data.local.room.FavoriteDao
import com.izo.apigithubuserapp.data.remote.api.ApiService
import com.izo.apigithubuserapp.data.remote.response.DetailUserResponse
import com.izo.apigithubuserapp.utils.AppExecutors
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Response

class UserRepository private constructor(
    private val apiService: ApiService,
    private val favoriteDao: FavoriteDao,
    private val appExecutors: AppExecutors,
    private val themePreferences: ThemePreferences
) {

    val result = MutableLiveData<Result<List<ItemsItem>>>()

    // Mengambil data detail user
    fun getDetailUser(username: String?): LiveData<Result<DetailUserResponse>> {
        val result = MutableLiveData<Result<DetailUserResponse>>()
        result.value = Result.Loading
        val client = apiService.getUser(username)
        client.enqueue(object : retrofit2.Callback<DetailUserResponse> {
            // jika koneksi sukses
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        result.value = Result.Success(responseBody)
                    }
                } else {
                    result.value = Result.Error(response.message().toString())
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                // jika koneksi gagal
                result.value = Result.Error(t.message.toString())
            }

        })
        return result
    }

    // Mengambil user dari key search
    fun findUser(username: String?): MutableLiveData<Result<List<ItemsItem>>> {
        result.value = Result.Loading
        val client = apiService.getListUser(username)
        client.enqueue(object : retrofit2.Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
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

    // input data detail ke database
    fun insertData(favoriteUser: FavoriteEntity) {
        appExecutors.diskIO.execute {
            favoriteDao.insertFavorite(favoriteUser)
        }
    }

    // Ambil data dari room
    fun getData(): LiveData<List<FavoriteEntity>> = favoriteDao.getFavorite()

    // Delete data user dari room
    fun deleteData(favoriteUser: FavoriteEntity) {
        appExecutors.diskIO.execute {
            favoriteDao.deleteFavorite(favoriteUser)
        }
    }

    // Check ada di favorite atau tidak
    fun isFavorite(userId: Int): LiveData<Boolean> = favoriteDao.isFavorite(userId)

    // Mengatur tema aplikasi
    fun getThemeSetting(): Flow<Boolean> = themePreferences.getThemeSetting()

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        themePreferences.saveThemeSetting(isDarkModeActive)
    }


    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            favoriteDao: FavoriteDao,
            appExecutors: AppExecutors,
            themePreferences: ThemePreferences
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, favoriteDao, appExecutors, themePreferences)
            }.also { instance = it }
    }
}