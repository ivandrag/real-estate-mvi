package com.example.presentation.shared.model

import com.example.domain.model.ListingBO
import com.example.presentation.utils.formatPrice

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
