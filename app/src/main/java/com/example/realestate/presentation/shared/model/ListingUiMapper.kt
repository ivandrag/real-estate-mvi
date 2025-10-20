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
        bedrooms = if (bedrooms == 0) "N/A" else "$bedrooms bed${if (bedrooms != 1) "s" else ""}",
        area = "${area.toInt()} m²",
        imageUrl = imageUrl,
        professional = professional.firstOrNull()?.uppercase() ?: "?",
        rooms = if (rooms == 0) "N/A" else "$rooms room${if (rooms != 1) "s" else ""}"
    )
}

private fun Double.formatPrice(): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale.getDefault()).apply {
        maximumFractionDigits = 0
    }
    return formatter.format(this)
}
