package com.example.data.remote

import com.example.data.networking.ListingsApi

class ListingDetailsRemoteDataSource(
    private val api: ListingsApi
) {

    suspend fun getListingDetails(id: Int) = api.getListingDetail(id)
}
