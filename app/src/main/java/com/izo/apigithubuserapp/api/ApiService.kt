package com.izo.apigithubuserapp.api

import com.izo.apigithubuserapp.BuildConfig
import com.izo.apigithubuserapp.response.DetailUserResponse
import com.izo.apigithubuserapp.ItemsItem
import com.izo.apigithubuserapp.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {


    @GET("/users/{username}")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    fun getUser(
        @Path("username") username: String?
    ): Call<DetailUserResponse>

    @GET("/search/users")
    fun getListUser(
        @Query("q") q: String
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