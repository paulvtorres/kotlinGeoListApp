package com.example.kotlingeolistapp.data.remote.repository

import com.example.kotlingeolistapp.data.remote.api.CountryApi
import com.example.kotlingeolistapp.domain.remote.mappers.toDetailDomain
import com.example.kotlingeolistapp.domain.remote.mappers.toDomain
import com.example.kotlingeolistapp.domain.remote.model.Country
import com.example.kotlingeolistapp.domain.remote.model.CountryDetail
import com.example.kotlingeolistapp.domain.remote.repository.CountryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class CountryRepositoryImpl @Inject constructor(
    private val api: CountryApi
) : CountryRepository {

    override fun getCountries(): Flow<List<Country>> = flow {
        val countries = api.getCountries().map { it.toDomain() }
        emit(countries)
    }

    override fun searchCountries(search: String): Flow<List<Country>> = flow {
        val countries = api.searchCountries(search).map { it.toDomain() }
        emit(countries)
    }

    override suspend fun getCountryDetail(name: String): CountryDetail {
        val dto = api.getCountryByName(name).first()
        return dto.toDetailDomain()
    }

}
