package com.example.presentation.allListings

sealed class AllListingsIntent {
    data object GetAllListings : AllListingsIntent()
    data class NavigateToListingDetails(val id: Int) : AllListingsIntent()
}
