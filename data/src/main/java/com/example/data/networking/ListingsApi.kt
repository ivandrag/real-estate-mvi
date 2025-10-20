package com.example.data.networking

import com.example.data.model.AllListingsResponseDto
import com.example.data.model.ListingResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ListingsApi {

    @GET("listings.json")
    suspend fun getListings(): AllListingsResponseDto

    @GET("listings/{id}.json")
    suspend fun getListingDetail(@Path("id") id: Int): ListingResponseDto
}
