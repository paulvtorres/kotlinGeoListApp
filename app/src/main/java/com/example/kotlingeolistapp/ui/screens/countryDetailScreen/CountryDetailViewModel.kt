package com.example.kotlingeolistapp.ui.screens.countryDetailScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlingeolistapp.domain.remote.model.CountryDetail
import com.example.kotlingeolistapp.domain.remote.usecases.GetCountryDetailUseCase
import com.example.kotlingeolistapp.domain.util.Resource
import com.example.kotlingeolistapp.ui.state.CountryDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryDetailViewModel @Inject constructor(
    private val getCountryDetailUseCase: GetCountryDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(CountryDetailState())
    val state: StateFlow<CountryDetailState> = _state

    init {
        val countryName: String? = savedStateHandle["countryName"]
        countryName?.let { loadCountryDetail(it) }
    }

    fun loadCountryDetail(name: String) {
        viewModelScope.launch {
            _state.value = CountryDetailState(isLoading = true)
            when (val result = getCountryDetailUseCase(name)) {
                is Resource.Success -> _state.value = CountryDetailState(country = result.data)
                is Resource.NoInternet -> _state.value = CountryDetailState(noInternet = true)
                is Resource.Error -> _state.value = CountryDetailState(error = result.message)
                else -> {}
            }
        }
    }
}


