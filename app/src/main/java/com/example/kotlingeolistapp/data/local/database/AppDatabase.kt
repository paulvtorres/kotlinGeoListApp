package com.example.kotlingeolistapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kotlingeolistapp.data.local.FavoriteCountryDao
import com.example.kotlingeolistapp.data.local.model.FavoriteCountryEntity

@Database(
    entities = [FavoriteCountryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteCountryDao(): FavoriteCountryDao
}