package com.izo.apigithubuserapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
class FavoriteEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @field:ColumnInfo(name = "userId")
    val userId: String? = null,

    @field:ColumnInfo(name = "username")
    val username: String? = null,

    @field:ColumnInfo(name = "avatar")
    val avatar : String? = null,

    @field:ColumnInfo(name = "url")
    val url: String? = null


)