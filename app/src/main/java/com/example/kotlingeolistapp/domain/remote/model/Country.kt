package com.example.kotlingeolistapp.domain.remote.model

data class Country(
    val code: String,
    val name: String,
    val officialName: String,
    val flagUrl: String,
    val capital: String,
    val isFavorite: Boolean = false
)
