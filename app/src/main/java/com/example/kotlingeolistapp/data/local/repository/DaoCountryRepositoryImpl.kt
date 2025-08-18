package com.example.kotlingeolistapp.data.local.repository

import com.example.kotlingeolistapp.data.local.model.FavoriteCountryEntity
import com.example.kotlingeolistapp.data.local.FavoriteCountryDao
import com.example.kotlingeolistapp.domain.local.repository.DaoCountryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class DaoCountryRepositoryImpl @Inject constructor(
    private val dao: FavoriteCountryDao
) : DaoCountryRepository {

    override fun getFavorites(): Flow<List<FavoriteCountryEntity>> = dao.getFavorites()

    override suspend fun addFavorite(entity: FavoriteCountryEntity) = dao.addFavorite(entity)

    override suspend fun deleteByCode(code: String) = dao.deleteByCode(code)


}
