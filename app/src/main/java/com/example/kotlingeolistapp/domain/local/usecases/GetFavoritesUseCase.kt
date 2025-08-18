package com.example.kotlingeolistapp.domain.local.usecases

import com.example.kotlingeolistapp.data.local.model.FavoriteCountryEntity
import com.example.kotlingeolistapp.domain.local.repository.DaoCountryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val repository: DaoCountryRepository
) {
    operator fun invoke(): Flow<List<FavoriteCountryEntity>> = repository.getFavorites()
}