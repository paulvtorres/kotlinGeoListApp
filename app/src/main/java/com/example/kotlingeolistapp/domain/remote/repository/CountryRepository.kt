package com.example.kotlingeolistapp.domain.remote.repository

import com.example.kotlingeolistapp.domain.remote.model.Country
import com.example.kotlingeolistapp.domain.remote.model.CountryDetail
import kotlinx.coroutines.flow.Flow

interface CountryRepository {
    fun getCountries(): Flow<List<Country>>
    fun searchCountries(search: String): Flow<List<Country>>
    suspend fun getCountryDetail(name: String): CountryDetail
}