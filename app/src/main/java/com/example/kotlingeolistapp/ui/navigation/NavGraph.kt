package com.example.kotlingeolistapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.kotlingeolistapp.ui.screens.countriesListScreen.CountriesListScreen
import com.example.kotlingeolistapp.ui.screens.countryDetailScreen.CountryDetailScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "countriesList"
    ) {
        composable("countriesList") {
            CountriesListScreen(
                onCountryClick = { countryName ->
                    navController.navigate("countryDetail/$countryName")
                }
            )
        }
        composable(
            route = "countryDetail/{countryName}",
            arguments = listOf(navArgument("countryName") { type = NavType.StringType })
        ) { backStackEntry ->
            val countryName = backStackEntry.arguments?.getString("countryName") ?: ""
            CountryDetailScreen(
                countryName = countryName,
                onBack = { navController.popBackStack() }
            )
        }
    }
}


