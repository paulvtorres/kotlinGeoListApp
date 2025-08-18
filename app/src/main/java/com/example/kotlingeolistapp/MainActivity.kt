package com.example.kotlingeolistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.kotlingeolistapp.ui.navigation.AppNavGraph
import com.example.kotlingeolistapp.ui.theme.KotlinGeoListAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            KotlinGeoListAppTheme {
                GeoListApp()
            }
        }
    }
}

@Composable
fun GeoListApp() {
    val navController = rememberNavController()
    AppNavGraph(navController = navController)
}