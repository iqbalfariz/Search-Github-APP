package com.izo.apigithubuserapp.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.izo.apigithubuserapp.FollowersFragment
import com.izo.apigithubuserapp.ItemsItem
import com.izo.apigithubuserapp.UserResponse
import com.izo.apigithubuserapp.api.ApiConfig
import retrofit2.Call
import retrofit2.Response

class FollowersViewModel: ViewModel() {

    private val _listFollowers = MutableLiveData<List<ItemsItem>>()
    val listFollowers: LiveData<List<ItemsItem>> = _listFollowers


    var username: String? = "username"


    companion object {
        private const val TAG = "FollowersFragment"
    }

//    init {
//        findFollowers(username)
//    }

    fun findFollowers(username: String?) {
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : retrofit2.Callback<List<ItemsItem>>{
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                // jika koneksi berhasil
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listFollowers.value = responseBody
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }
}