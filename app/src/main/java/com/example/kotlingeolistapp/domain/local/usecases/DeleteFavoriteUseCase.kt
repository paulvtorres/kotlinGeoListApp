package com.example.kotlingeolistapp.domain.local.usecases

import com.example.kotlingeolistapp.domain.local.repository.DaoCountryRepository
import javax.inject.Inject

class DeleteFavoriteUseCase @Inject constructor(
    private val repository: DaoCountryRepository
) {
    suspend operator fun invoke(code: String) = repository.deleteByCode(code)
}