package com.example.kotlingeolistapp.ui.state

import com.example.kotlingeolistapp.domain.remote.model.CountryDetail

data class CountryDetailState(
    val isLoading: Boolean = false,
    val country: CountryDetail? = null,
    val noInternet: Boolean = false,
    val error: String? = null
)