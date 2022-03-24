package com.izo.apigithubuserapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.izo.apigithubuserapp.data.local.entity.FavoriteEntity

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavorite(favorite: FavoriteEntity)
    @Update
    fun updateFavorite(favorite: FavoriteEntity)
    @Delete
    fun deleteFavorite(favorite: FavoriteEntity)
    @Query("SELECT * from favorite ORDER BY id ASC")
    fun getFavorite(): LiveData<List<FavoriteEntity>>
}