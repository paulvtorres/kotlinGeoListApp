package com.example.kotlingeolistapp.domain.remote.mappers

import com.example.kotlingeolistapp.data.remote.dto.CountryDto
import com.example.kotlingeolistapp.domain.remote.model.Country

fun CountryDto.toDomain(): Country {
    return Country(
        code = "${this.cca2}${this.cca3}",
        name = this.name.common,
        officialName = this.name.official,
        flagUrl = this.flags.png,
        capital = this.capital?.firstOrNull() ?: "No Capital"
    )
}