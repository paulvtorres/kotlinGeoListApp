package com.example.kotlingeolistapp.ui.screens.countryDetailScreen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.kotlingeolistapp.ui.state.CountryDetailState

@Composable
fun DetailsOverlays(state: CountryDetailState) {
    when {
        state.isLoading -> {
            CountryDetailSkeleton()
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