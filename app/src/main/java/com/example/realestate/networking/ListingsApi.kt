package com.example.realestate.networking

import com.example.realestate.data.model.ListingsResponseDto
import retrofit2.http.GET

interface ListingsApi {

    @GET("listings.json")
    suspend fun getListings(): ListingsResponseDto
}
