package com.izo.apigithubuserapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.izo.apigithubuserapp.data.local.entity.FavoriteEntity

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite: List<FavoriteEntity>)
    @Update
    fun update(favorite: List<FavoriteEntity>)
    @Delete
    fun delete(favorite: List<FavoriteEntity>)
    @Query("SELECT * from favorite ORDER BY id ASC")
    fun getFavorite(): LiveData<List<FavoriteEntity>>
}