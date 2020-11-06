package com.alexstibbons.showcase.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FaveEntity::class],
    version = 1
)
abstract class FaveDatabase : RoomDatabase() {
    abstract fun faveDao(): FaveDao
}