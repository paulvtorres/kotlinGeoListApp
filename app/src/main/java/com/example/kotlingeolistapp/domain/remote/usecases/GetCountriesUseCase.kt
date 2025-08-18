package com.example.kotlingeolistapp.domain.remote.usecases

import com.example.kotlingeolistapp.domain.remote.model.Country
import com.example.kotlingeolistapp.domain.remote.repository.CountryRepository
import com.example.kotlingeolistapp.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import java.io.IOException
import javax.inject.Inject

class GetCountriesUseCase @Inject constructor(
    private val repository: CountryRepository
) {
    operator fun invoke(): Flow<Resource<List<Country>>> =
        repository.getCountries()
            .map { countries -> Resource.Success(countries) as Resource<List<Country>> }
            .onStart { emit(Resource.Loading) }
            .catch { e ->
                when (e) {
                    is IOException -> emit(Resource.NoInternet)
                    else -> emit(Resource.Error(e.message ?: "Ocurrió un error inesperado", e))
                }
            }
}
