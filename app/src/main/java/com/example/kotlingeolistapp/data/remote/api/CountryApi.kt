package com.example.kotlingeolistapp.data.remote.api

import com.example.kotlingeolistapp.core.Constants.ALL_COUNTRIES
import com.example.kotlingeolistapp.core.Constants.BYCOUNTRY
import com.example.kotlingeolistapp.data.remote.dto.CountryDetailDto
import com.example.kotlingeolistapp.data.remote.dto.CountryDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CountryApi {
    @GET(ALL_COUNTRIES)
    suspend fun getCountries(): List<CountryDto>

    @GET(BYCOUNTRY)
    suspend fun getCountryByName(
        @Path("name") name: String,
        @Query("fullText") fullText: Boolean = false
    ): List<CountryDetailDto>

    @GET(BYCOUNTRY)
    suspend fun searchCountries(
        @Path("name") name: String,
    ): List<CountryDto>
}