package com.example.kotlingeolistapp.ui.screens.countriesListScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.lifecycle.compose.*
import com.example.kotlingeolistapp.ui.screens.countriesListScreen.components.CountryList
import com.example.kotlingeolistapp.ui.screens.countriesListScreen.components.SearchBarWithMic
import com.example.kotlingeolistapp.ui.screens.countriesListScreen.components.UiOverlays
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged



@OptIn(ExperimentalMaterial3Api::class, FlowPreview::class)
@Composable
fun CountriesListScreen(
    viewModel: CountriesViewModel = hiltViewModel(),
    onCountryClick: (String) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var searchText by remember { mutableStateOf("") }
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }

    LaunchedEffect(searchText) {
        snapshotFlow { searchText }
            .debounce(500)
            .distinctUntilChanged()
            .collectLatest { query ->
                viewModel.getCountries(query)
            }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text("Countries")
        } }) },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Default.Public, contentDescription = "All") },
                    label = { Text("Todos") }
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Default.Star, contentDescription = "Favorites") },
                    label = { Text("Favoritos") }
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(Modifier.fillMaxSize()) {

                if (selectedTab == 0) {
                    SearchBarWithMic(
                        searchText = searchText,
                        onSearchChange = { searchText = it }
                    )
                }

                when (selectedTab) {
                    0 -> CountryList(
                        countries = state.countries,
                        favorites = state.favorites.map { it.code },
                        onCountryClick = { onCountryClick(it.name) },
                        onToggleFavorite = { viewModel.toggleFavorite(it) }
                    )
                    1 -> CountryList(
                        countries = state.favorites,
                        favorites = state.favorites.map { it.code },
                        onCountryClick = { onCountryClick(it.name) },
                        onToggleFavorite = { viewModel.toggleFavorite(it) }
                    )
                }
                UiOverlays(state = state, modifier = Modifier.weight(1f))
            }
        }
    }
}