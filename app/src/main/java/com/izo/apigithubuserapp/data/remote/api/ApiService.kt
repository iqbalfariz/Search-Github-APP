package com.izo.apigithubuserapp.data.remote.api

import com.izo.apigithubuserapp.BuildConfig
import com.izo.apigithubuserapp.ItemsItem
import com.izo.apigithubuserapp.UserResponse
import com.izo.apigithubuserapp.data.remote.response.DetailUserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {


    @GET("/users/{username}")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    fun getUser(
        @Path("username") username: String?
    ): Call<DetailUserResponse>

    @GET("/search/users")
    fun getListUser(
        @Query("q") q: String?
    ): Call<UserResponse>

    @GET("/users/{username}/followers")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    fun getFollowers(
        @Path("username") username: String?
    ): Call<List<ItemsItem>>

    @GET("/users/{username}/following")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    fun getFollowing(
        @Path("username") username: String?
    ): Call<List<ItemsItem>>

}