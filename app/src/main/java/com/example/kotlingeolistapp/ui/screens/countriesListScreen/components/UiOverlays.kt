package com.example.kotlingeolistapp.ui.screens.countriesListScreen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.kotlingeolistapp.ui.state.CountriesUiState

@Composable
fun UiOverlays(state: CountriesUiState,modifier: Modifier = Modifier) {
    when {
        state.isLoading -> {
                CountryListSkeleton(modifier)
        }
        state.noInternet -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Sin conexión a internet")
            }
        }
        state.error != null -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: ${state.error}")
            }
        }
    }
}

