package com.example.realestate.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.realestate.presentation.allListings.AllListingsScreen
import com.example.realestate.presentation.listingDetails.ListingDetailsScreen

@Composable
fun NavHostContainer(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Listings.route
    ) {
        composable(route = Screen.Listings.route) {
            AllListingsScreen {
                navController.navigate(Screen.ListingDetails.createRoute(id))
            }
        }
        composable(
            route = Screen.ListingDetails.route,
            arguments = listOf(
                navArgument("listingId") { type = NavType.IntType }
            )
        ) {
            ListingDetailsScreen {
                navController.popBackStack()
            }
        }
    }
}

sealed class Screen(val route: String) {
    data object Listings : Screen("listings")
    data object ListingDetails : Screen("details/{listingId}") {
        fun createRoute(listingId: Int) = "details/$listingId"
    }
}

