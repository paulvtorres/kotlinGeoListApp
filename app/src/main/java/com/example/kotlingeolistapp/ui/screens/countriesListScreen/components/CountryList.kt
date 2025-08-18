package com.example.kotlingeolistapp.ui.screens.countriesListScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.kotlingeolistapp.domain.remote.model.Country

@Composable
fun CountryList(
    countries: List<Country>,
    favorites: List<String>,
    onCountryClick: (Country) -> Unit,
    onToggleFavorite: (Country) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 2.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        items(countries) { country ->
            CountryItem(
                country = country.copy(isFavorite = favorites.contains(country.code)),
                onClick = { onCountryClick(country) },
                onToggleFavorite = onToggleFavorite
            )
        }
    }
}

