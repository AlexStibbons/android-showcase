package com.alexstibbons.showcase.database

import androidx.room.*

interface FaveCache {
    suspend fun getFaves(): List<FaveEntity>
    suspend fun getFavesIds(): List<Int>
    suspend fun addToFave(fave: FaveEntity): Long
    suspend fun removeFave(id: Int)
    suspend fun clear()
}

@Database(
    entities = [FaveEntity::class],
    version = 2
)
internal abstract class FaveCacheImpl : RoomDatabase(), FaveCache {
    abstract fun faveDao(): FaveDao

    override suspend fun addToFave(fave: FaveEntity): Long = faveDao().addToFave(fave)

    override suspend fun getFaves(): List<FaveEntity> = faveDao().getFaves()

    override suspend fun getFavesIds(): List<Int> = faveDao().getFavesIds()

    override suspend fun removeFave(id: Int) = faveDao().removeFave(id)

    override suspend fun clear() = faveDao().clear()
}