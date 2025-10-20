package com.example.realestate.presentation.allListings.model

import com.example.realestate.domain.model.ListingBO
import com.example.realestate.presentation.shared.model.Listing
import com.example.realestate.presentation.shared.model.toListingUI
import java.text.NumberFormat
import java.util.Locale

fun List<ListingBO>.toListingUiList(): List<Listing> {
    return map { it.toListingUI() }
}
