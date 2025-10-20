package com.example.realestate.data.mapper

import com.example.realestate.data.model.ListingDto
import com.example.realestate.domain.model.ListingBO

class AllListingsMapper {

    fun toListingBO(dto: ListingDto) = ListingBO(
        id = dto.id,
        city = dto.city,
        propertyType = dto.propertyType,
        price = dto.price,
        bedrooms = dto.bedrooms ?: 0,
        area = dto.area ?: 0.0,
        imageUrl = dto.url,
        professional = dto.professional,
        rooms = dto.rooms ?: 0
    )

    fun toAllListings(allListingsResponse: List<ListingDto>) =
        allListingsResponse.map { toListingBO(it) }
}
