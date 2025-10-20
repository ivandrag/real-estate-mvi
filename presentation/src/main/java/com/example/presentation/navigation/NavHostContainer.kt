package com.example.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.presentation.allListings.AllListingsScreen
import com.example.presentation.listingDetails.ListingDetailsScreen

private object NavConstants {
    const val ROUTE_LISTINGS = "listings"
    const val ROUTE_LISTING_DETAILS = "details/{listingId}"
    const val ARG_LISTING_ID = "listingId"
}

@Composable
fun NavHostContainer(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Listings.route
    ) {
        composable(route = Screen.Listings.route) {
            AllListingsScreen { id ->
                navController.navigate(Screen.ListingDetails.createRoute(id))
            }
        }
        composable(
            route = Screen.ListingDetails.route,
            arguments = listOf(
                navArgument(NavConstants.ARG_LISTING_ID) { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val listingId = backStackEntry.arguments?.getInt(NavConstants.ARG_LISTING_ID) ?: 0
            ListingDetailsScreen(
                listingId = listingId,
                onBackButtonTapped = { navController.popBackStack() }
            )
        }
    }
}

sealed class Screen(val route: String) {
    data object Listings : Screen(NavConstants.ROUTE_LISTINGS)
    data object ListingDetails : Screen(NavConstants.ROUTE_LISTING_DETAILS) {
        fun createRoute(listingId: Int) = "details/$listingId"
    }
}
