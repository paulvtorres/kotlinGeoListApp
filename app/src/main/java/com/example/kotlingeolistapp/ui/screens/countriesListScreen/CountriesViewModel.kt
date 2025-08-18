package com.example.kotlingeolistapp.ui.screens.countriesListScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlingeolistapp.core.Constants.STRING_EMPTY
import com.example.kotlingeolistapp.data.local.model.FavoriteCountryEntity
import com.example.kotlingeolistapp.domain.remote.model.Country
import com.example.kotlingeolistapp.domain.local.usecases.AddFavoriteUseCase
import com.example.kotlingeolistapp.domain.local.usecases.DeleteFavoriteUseCase
import com.example.kotlingeolistapp.domain.remote.usecases.GetCountriesUseCase
import com.example.kotlingeolistapp.domain.local.usecases.GetFavoritesUseCase
import com.example.kotlingeolistapp.domain.remote.usecases.SearchCountriesUseCase
import com.example.kotlingeolistapp.domain.util.ConnectivityObserver
import com.example.kotlingeolistapp.domain.util.Resource
import com.example.kotlingeolistapp.ui.state.CountriesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(
    private val getCountriesUseCase: GetCountriesUseCase,
    private val searchCountriesUseCase: SearchCountriesUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {

    private val _state = MutableStateFlow(CountriesUiState())
    val state: StateFlow<CountriesUiState> = _state

    private var fullCountriesCache: List<Country> = emptyList()
    private var favoritesCache: List<String> = emptyList()

    private var lastQuery: String = STRING_EMPTY

    init {
        loadAllCountries()
        observeFavorites()
        observeConnectivity()
    }

    private fun observeConnectivity() {
        viewModelScope.launch {
            connectivityObserver.observe().collect { status ->
                if (status == ConnectivityObserver.Status.Available) {
                    _state.value = _state.value.copy(noInternet = false)
                    if (fullCountriesCache.isEmpty()) {
                        loadAllCountries()
                    }
                }
            }
        }
    }

    private fun loadAllCountries() {
        viewModelScope.launch {
            getCountriesUseCase().collect { result ->
                when (result) {
                    is Resource.Loading -> _state.value = _state.value.copy(isLoading = true)

                    is Resource.Success -> {
                        fullCountriesCache = result.data
                        refreshCountriesAndFavorites()
                        _state.value = _state.value.copy(isLoading = false)
                    }

                    is Resource.Error -> _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.message
                    )

                    is Resource.NoInternet -> _state.value = _state.value.copy(
                        isLoading = false,
                        noInternet = true,
                        error = null
                    )
                }
            }
        }
    }

    fun getCountries(search: String) {
        lastQuery = search

        if (search.isBlank() || search.length == 1) {
            refreshCountriesAndFavorites()
            return
        }

        viewModelScope.launch {
            searchCountriesUseCase(search).collect { result ->
                when (result) {
                    is Resource.Loading -> _state.value = _state.value.copy(isLoading = true)

                    is Resource.Success -> {
                        val merged = result.data.map {
                            it.copy(isFavorite = favoritesCache.contains(it.code))
                        }
                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = null,
                            countries = merged
                        )
                    }

                    is Resource.Error -> {
                        val msg = if (
                            result.message.contains("404") ||
                            result.message.contains("Not Found", true)
                        ) "No existen coincidencias" else result.message

                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = msg,
                            countries = emptyList()
                        )
                    }

                    is Resource.NoInternet -> _state.value = _state.value.copy(
                        isLoading = false,
                        noInternet = true,
                        error = null
                    )
                }
            }
        }
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            getFavoritesUseCase().collect { favEntities ->
                favoritesCache = favEntities.map { it.code }
                refreshCountriesAndFavorites()
            }
        }
    }

    private fun refreshCountriesAndFavorites() {
        val mergedCountries =
            fullCountriesCache.map { it.copy(isFavorite = favoritesCache.contains(it.code)) }

        val favoritesList = mergedCountries.filter { it.isFavorite }

        val isSearching = lastQuery.isNotBlank() && lastQuery.length >= 2
        val visibleCountries =
            if (isSearching) _state.value.countries.map {
                it.copy(isFavorite = favoritesCache.contains(it.code))
            } else mergedCountries

        _state.value = _state.value.copy(
            countries = visibleCountries,
            favorites = favoritesList,
            error = null
        )
    }

    fun toggleFavorite(country: Country) {
        viewModelScope.launch {
            if (country.isFavorite) {
                deleteFavoriteUseCase(country.code)
            } else {
                addFavoriteUseCase(FavoriteCountryEntity(code = country.code))
            }
        }
    }
}
