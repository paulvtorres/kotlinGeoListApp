package com.example.kotlingeolistapp.ui.screens.countryDetailScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import coil.compose.rememberAsyncImagePainter
import com.example.kotlingeolistapp.ui.screens.countryDetailScreen.components.CarDriverSideColumn
import com.example.kotlingeolistapp.ui.screens.countryDetailScreen.components.DetailColumn
import com.example.kotlingeolistapp.ui.screens.countryDetailScreen.components.DetailsOverlays

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryDetailScreen(
    countryName: String,
    viewModel: CountryDetailViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    LaunchedEffect(countryName) {
        viewModel.loadCountryDetail(countryName)
    }
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(
                    text = "Countries",
                    color = Color(0xFF03A9F4)
                ) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Back",
                            tint = Color(0xFF03A9F4)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            state.country?.let { country ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Image(
                            painter = rememberAsyncImagePainter(country.flagUrl),
                            contentDescription = "Flag of ${country.name}",
                            modifier = Modifier
                                .width(120.dp)
                                .height(90.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            contentScale = ContentScale.Fit
                        )
                        Spacer(Modifier.height(12.dp))
                        Text(
                            text = country.name,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = country.officialName,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }

                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(24.dp)
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                horizontalAlignment = Alignment.Start
                            ) {
                                DetailColumn("Coat of Arms", imageUrl = country.coatOfArmsUrl)
                                DetailColumn("Region", text = country.region)
                                DetailColumn("Subregion", text = country.subregion)
                                DetailColumn("Capital", text = country.capital)
                                DetailColumn("Area", text = "${country.area} km²")
                            }
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                horizontalAlignment = Alignment.Start
                            ) {
                                DetailColumn(
                                    "Population",
                                    text = "%,d".format(country.population ?: 0)
                                )
                                DetailColumn("Language(s)", text = country.languages.joinToString())
                                country.carDriverSide?.let { CarDriverSideColumn(it) }
                                DetailColumn("Currencies", text = country.currencies.joinToString())
                                DetailColumn("Timezones", text = country.timezones.joinToString())
                            }
                        }
                    }
                }
            }
            DetailsOverlays(state = state)
        }
    }
}




