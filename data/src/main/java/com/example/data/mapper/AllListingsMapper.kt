package com.example.data.mapper

import com.example.data.model.ListingResponseDto
import com.example.domain.model.ListingBO

class AllListingsMapper {

    fun toListingBO(dto: ListingResponseDto) = ListingBO(
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

    fun toAllListings(allListingsResponse: List<ListingResponseDto>) =
        allListingsResponse.map { toListingBO(it) }
}
