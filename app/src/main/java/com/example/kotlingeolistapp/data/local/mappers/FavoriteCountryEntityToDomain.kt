package com.example.kotlingeolistapp.data.local.mappers

import com.example.kotlingeolistapp.data.local.model.FavoriteCountryEntity
import com.example.kotlingeolistapp.domain.remote.model.Country

fun FavoriteCountryEntity.toDomain() = Country(
    code = code,
    name = "",
    flagUrl = "",
    officialName = "",
    capital = ""
)