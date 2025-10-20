package com.example.realestate.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.realestate.presentation.allListings.AllListingsScreen

@Composable
fun NavHostContainer(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Listings.route
    ) {
        composable(route = Screen.Listings.route) {
            AllListingsScreen()
        }
    }
}

sealed class Screen(val route: String) {
    data object Listings : Screen("listings")
}
