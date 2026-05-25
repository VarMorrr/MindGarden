package com.example.mindgarden.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(
    entities = [FlowerEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun flowerDao(): FlowerDao

    companion object {
        const val DATABASE_NAME = "mindgarden.db"
    }
}
