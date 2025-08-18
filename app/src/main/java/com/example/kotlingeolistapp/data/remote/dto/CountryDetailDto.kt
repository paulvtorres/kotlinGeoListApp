package com.example.kotlingeolistapp.data.remote.dto

data class CountryDetailDto(
    val name: NameDetailDto,
    val flags: FlagDto,
    val coatOfArms: CoatOfArmsDto?,
    val region: String?,
    val subregion: String?,
    val capital: List<String>?,
    val area: Double?,
    val population: Long?,
    val languages: Map<String, String>?,
    val car: CarDto?,
    val currencies: Map<String, CurrencyDto>?,
    val timezones: List<String>?
)

data class NameDetailDto(
    val common: String,
    val official: String
)

data class FlagDto(
    val png: String?,
    val svg: String?
)

data class CoatOfArmsDto(
    val png: String?,
    val svg: String?
)

data class CarDto(
    val side: String?
)

data class CurrencyDto(
    val name: String?,
    val symbol: String?
)