package com.example.kotlingeolistapp.domain.remote.mappers

import com.example.kotlingeolistapp.data.remote.dto.CountryDetailDto
import com.example.kotlingeolistapp.domain.remote.model.CountryDetail

fun CountryDetailDto.toDetailDomain(): CountryDetail {
    return CountryDetail(
        name = name.common,
        officialName = name.official,
        flagUrl = flags.png ?: flags.svg.orEmpty(),
        coatOfArmsUrl = coatOfArms?.png ?: coatOfArms?.svg,
        region = region,
        subregion = subregion,
        capital = capital?.firstOrNull(),
        area = area,
        population = population,
        languages = languages?.values?.toList() ?: emptyList(),
        carDriverSide = car?.side,
        currencies = currencies?.values?.map {
            "${it.name} (${it.symbol ?: ""})"
        } ?: emptyList(),
        timezones = timezones ?: emptyList()
    )
}