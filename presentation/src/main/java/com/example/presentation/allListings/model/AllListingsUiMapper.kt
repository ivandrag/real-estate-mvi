package com.example.presentation.allListings.model

import com.example.domain.model.ListingBO
import com.example.presentation.shared.model.Listing
import com.example.presentation.shared.model.toListingUI

fun List<ListingBO>.toListingUiList(): List<Listing> {
    return map { it.toListingUI() }
}
