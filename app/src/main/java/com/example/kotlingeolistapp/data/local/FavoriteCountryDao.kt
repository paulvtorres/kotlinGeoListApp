package com.example.kotlingeolistapp.data.local

import androidx.room.*
import com.example.kotlingeolistapp.data.local.model.FavoriteCountryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCountryDao {

    @Query("SELECT * FROM favorite_countries")
    fun getFavorites(): Flow<List<FavoriteCountryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(country: FavoriteCountryEntity)

    @Query("DELETE FROM favorite_countries WHERE code = :code")
    suspend fun deleteByCode(code: String)

}
