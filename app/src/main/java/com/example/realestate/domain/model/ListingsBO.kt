package com.example.realestate.domain.model

data class ListingBO(
    val id: Int,
    val city: String,
    val propertyType: String,
    val price: Double,
    val bedrooms: Int,
    val area: Double,
    val imageUrl: String?,
    val professional: String,
    val rooms: Int
)
