package com.example.realestate.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AllListingsResponseDto(
    @SerialName("items") val items: List<ListingResponseDto>
)

