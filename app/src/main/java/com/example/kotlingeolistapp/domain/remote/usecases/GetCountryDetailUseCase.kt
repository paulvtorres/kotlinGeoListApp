package com.example.kotlingeolistapp.domain.remote.usecases

import com.example.kotlingeolistapp.domain.remote.model.CountryDetail
import com.example.kotlingeolistapp.domain.remote.repository.CountryRepository
import com.example.kotlingeolistapp.domain.util.Resource
import java.io.IOException
import javax.inject.Inject

class GetCountryDetailUseCase @Inject constructor(
    private val repository: CountryRepository
) {
    suspend operator fun invoke(name: String): Resource<CountryDetail> =
        try {
            Resource.Success(repository.getCountryDetail(name))
        } catch (e: IOException) {
            Resource.NoInternet
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Error desconocido", e)
        }
}