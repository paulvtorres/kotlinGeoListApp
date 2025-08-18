package com.example.kotlingeolistapp.domain.local.repository

import com.example.kotlingeolistapp.data.local.model.FavoriteCountryEntity
import kotlinx.coroutines.flow.Flow

interface DaoCountryRepository {
    fun getFavorites(): Flow<List<FavoriteCountryEntity>>
    suspend fun addFavorite(entity: FavoriteCountryEntity)
    suspend fun deleteByCode(code: String)
}