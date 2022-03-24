package com.izo.apigithubuserapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class DetailUserResponse(

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("company")
    val company: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("public_repos")
    val publicRepos: Int,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("url")
    val url: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("location")
    val location: String,

    @field:SerializedName("followers")
    val followers: Int,

    @field:SerializedName("following")
    val following: Int
)
