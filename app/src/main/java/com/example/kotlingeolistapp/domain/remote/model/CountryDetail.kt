package com.example.kotlingeolistapp.domain.remote.model

data class CountryDetail(
    val name: String,
    val officialName: String,
    val flagUrl: String,
    val coatOfArmsUrl: String?,
    val region: String?,
    val subregion: String?,
    val capital: String?,
    val area: Double?,
    val population: Long?,
    val languages: List<String>,
    val carDriverSide: String?,
    val currencies: List<String>,
    val timezones: List<String>
)
