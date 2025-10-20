package com.example.realestate.data.remote

import com.example.realestate.networking.ListingsApi

class ListingDetailsRemoteDataSource(
    private val api: ListingsApi
) {

    suspend fun getListingDetails(id: Int) = api.getListingDetail(id)
}