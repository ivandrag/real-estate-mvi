package com.example.realestate.presentation.shared.model

data class Listing(
    val id: Int,
    val city: String,
    val propertyType: String,
    val price: String,
    val bedrooms: String,
    val area: String,
    val imageUrl: String?,
    val professional: String,
    val rooms: String
)