package com.example.kotlingeolistapp

import com.example.kotlingeolistapp.domain.remote.model.*
import com.example.kotlingeolistapp.domain.remote.repository.*
import com.example.kotlingeolistapp.domain.remote.usecases.*
import com.example.kotlingeolistapp.domain.util.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class GetCountriesUseCaseInstrumentedTest {

    private val repository: CountryRepository = mockk()
    private val useCase = GetCountriesUseCase(repository)
    @Test
    fun when_repository_returns_countries_then_emits_Loading_and_Success() = runTest {
        // Arrange
        val fakeCountries = listOf(
            Country(
                code = "ECU",
                name = "Ecuador",
                officialName = "Republic of Ecuador",
                flagUrl = "https://flagcdn.com/ec.png",
                capital = "Quito"
            )
        )
        coEvery { repository.getCountries() } returns flow { emit(fakeCountries) }

        // Act
        val emissions: List<Resource<List<Country>>> = useCase().toList()

        // Assert
        assertEquals(Resource.Loading, emissions[0])
        assertEquals(Resource.Success(fakeCountries), emissions[1])
    }

    @Test
    fun when_repository_throws_IOException_then_emits_Loading_and_NoInternet() = runTest {
        // Arrange
        coEvery { repository.getCountries() } returns flow { throw IOException("No internet") }

        // Act
        val emissions = useCase().toList()

        // Assert
        assertEquals(Resource.Loading, emissions[0])
        assertEquals(Resource.NoInternet, emissions[1])
    }

    @Test
    fun when_repository_throws_generic_error_then_emits_Loading_and_Error() = runTest {
        // Arrange
        coEvery { repository.getCountries() } returns flow { throw RuntimeException("Boom") }

        // Act
        val emissions = useCase().toList()

        // Assert
        assertEquals(Resource.Loading, emissions[0])
        val error = emissions[1] as Resource.Error
        assertEquals("Boom", error.message)
    }
}

