package com.alexstibbons.showcase.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
internal interface FaveDao {
    
    @Query("SELECT * FROM faveentity")
    suspend fun getFaves(): List<FaveEntity>

    @Query("SELECT faveentity.id FROM faveentity")
    suspend fun getFavesIds(): List<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFave(fave: FaveEntity): Long

    @Query("DELETE FROM faveentity WHERE faveentity.id == :id")
    suspend fun removeFave(id: Int)
}