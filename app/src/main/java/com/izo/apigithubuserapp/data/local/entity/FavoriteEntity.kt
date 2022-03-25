package com.izo.apigithubuserapp.data.local.entity

import androidx.annotation.ColorInt
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
class FavoriteEntity(

//    @PrimaryKey(autoGenerate = true)
//    @ColumnInfo(name = "id")
//    var id: Int = 0,

    @PrimaryKey
    @field:ColumnInfo(name = "userId")
    val userId: Int? = null,

    @field:ColumnInfo(name = "username")
    val username: String? = null,

    @field:ColumnInfo(name = "avatar")
    val avatar : String? = null,

    @field:ColumnInfo(name = "url")
    val url: String? = null

)