package com.example.kotlingeolistapp.ui.state

import com.example.kotlingeolistapp.domain.remote.model.Country

data class CountriesUiState(
    val isLoading: Boolean = false,
    val countries: List<Country> = emptyList(),
    val favorites: List<Country> = emptyList(),
    val error: String? = null,
    val noInternet: Boolean = false
)