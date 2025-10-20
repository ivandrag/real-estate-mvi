package com.example.realestate.presentation.allListings

import com.example.realestate.domain.model.ListingBO
import java.text.NumberFormat
import java.util.Locale

fun ListingBO.toUI(): Listing {
    return Listing(
        id = id,
        city = city,
        propertyType = propertyType,
        price = price.formatPrice(),
        bedrooms = if (bedrooms == 0)
            "" else "$bedrooms bed${if (bedrooms != 1) "s" else ""}",
        area = "${area.toInt()} m²",
        imageUrl = imageUrl,
        professional = professional,
        rooms = if (rooms == 0) "" else "$rooms room${if (rooms != 1) "s" else ""}"
    )
}

fun List<ListingBO>.toUI(): List<Listing> {
    return map { it.toUI() }
}

private fun Double.formatPrice(): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale.getDefault()).apply {
        maximumFractionDigits = 0
    }
    return formatter.format(this)
}
