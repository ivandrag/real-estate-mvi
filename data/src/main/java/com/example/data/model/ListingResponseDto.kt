package com.example.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListingResponseDto(
    @SerialName("id") val id: Int,
    @SerialName("bedrooms") val bedrooms: Int? = null,
    @SerialName("city") val city: String,
    @SerialName("area") val area: Double? = null,
    @SerialName("url") val url: String? = null,
    @SerialName("price") val price: Double,
    @SerialName("professional") val professional: String,
    @SerialName("propertyType") val propertyType: String,
    @SerialName("offerType") val offerType: Int,
    @SerialName("rooms") val rooms: Int? = null
)