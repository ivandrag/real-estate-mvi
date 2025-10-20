package com.example.realestate.presentation.listingDetails

sealed class ListingDetailsIntent {
    data object GoBack : ListingDetailsIntent()
}
