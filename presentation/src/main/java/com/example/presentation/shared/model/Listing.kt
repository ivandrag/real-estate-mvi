package com.example.presentation.shared.model

data class Listing(
    val id: Int,
    val city: String,
    val propertyType: String,
    val price: String,
    val bedrooms: Int,
    val area: Int,
    val imageUrl: String?,
    val professional: String,
    val rooms: Int
)
