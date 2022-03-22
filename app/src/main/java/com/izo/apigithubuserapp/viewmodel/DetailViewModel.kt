package com.izo.apigithubuserapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.izo.apigithubuserapp.api.ApiConfig
import com.izo.apigithubuserapp.response.DetailUserResponse
import retrofit2.Call
import retrofit2.Response

class DetailViewModel: ViewModel() {
    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = _detailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    var username: String? = "username"


    companion object {
        private const val TAG = "DetailActivity"
    }


    fun findDetailUser(username: String?) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(username)
        client.enqueue(object : retrofit2.Callback<DetailUserResponse>{
            // jika koneksi berhasil
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                if(response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _isLoading.value = false
                        _detailUser.value = responseBody
                    }
                } else {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }
}