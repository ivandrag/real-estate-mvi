package com.example.presentation.listingDetails

sealed class ListingDetailsIntent {
    data object GoBack : ListingDetailsIntent()
    data class LoadListingDetails(val id: Int) : ListingDetailsIntent()
}
