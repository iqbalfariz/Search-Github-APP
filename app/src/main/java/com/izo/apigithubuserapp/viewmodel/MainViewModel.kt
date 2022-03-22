package com.izo.apigithubuserapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.izo.apigithubuserapp.ItemsItem
import com.izo.apigithubuserapp.UserResponse
import com.izo.apigithubuserapp.api.ApiConfig
import retrofit2.Call
import retrofit2.Response

class MainViewModel: ViewModel() {

    private val _listData = MutableLiveData<List<ItemsItem>>()
    val listData: LiveData<List<ItemsItem>> = _listData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    companion object {
        private const val TAG = "MainActivity"
    }

    init {
        findUser("iqbal")
    }

    fun findUser(USERNAME: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListUser(USERNAME)
        client.enqueue(object : retrofit2.Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                // jika koneksi sukses
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listData.value = responseBody?.items
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                // jika koneksi gagal
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }



}