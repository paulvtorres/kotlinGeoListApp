package com.example.kotlingeolistapp.data.remote.dto

data class CountryDto(
    val name: NameDto,
    val flags: FlagsDto,
    val capital: List<String>?,
    val cca2: String,
    val cca3: String,
)

data class FlagsDto(
    val png: String,
    val svg: String
)

data class NameDto(
    val common: String,
    val official: String
)