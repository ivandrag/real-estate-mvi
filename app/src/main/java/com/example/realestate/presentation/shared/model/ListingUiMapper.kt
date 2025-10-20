package com.example.realestate.presentation.shared.model

import com.example.realestate.domain.model.ListingBO
import java.text.NumberFormat
import java.util.Locale

fun ListingBO.toListingUI(): Listing {
    return Listing(
        id = id,
        city = city,
        propertyType = propertyType,
        price = price.formatPrice(),
        bedrooms = bedrooms,
        area = area.toInt(),
        imageUrl = imageUrl,
        professional = professional,
        rooms = rooms
    )
}

private fun Double.formatPrice(): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale.getDefault()).apply {
        maximumFractionDigits = 0
    }
    return formatter.format(this)
}
