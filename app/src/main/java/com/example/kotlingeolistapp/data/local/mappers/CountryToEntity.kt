package com.example.kotlingeolistapp.data.local.mappers

import com.example.kotlingeolistapp.data.local.model.FavoriteCountryEntity
import com.example.kotlingeolistapp.domain.remote.model.Country

fun Country.toEntity() = FavoriteCountryEntity(
    code = code
)