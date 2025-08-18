package com.example.kotlingeolistapp.domain.local.usecases

import com.example.kotlingeolistapp.data.local.model.FavoriteCountryEntity
import com.example.kotlingeolistapp.domain.local.repository.DaoCountryRepository
import javax.inject.Inject

class AddFavoriteUseCase @Inject constructor(
    private val repository: DaoCountryRepository
) {
    suspend operator fun invoke(entity: FavoriteCountryEntity) = repository.addFavorite(entity)
}