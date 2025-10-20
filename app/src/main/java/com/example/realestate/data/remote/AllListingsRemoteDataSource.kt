package com.example.realestate.data.remote

import com.example.realestate.networking.ListingsApi

class AllListingsRemoteDataSource(
    private val api: ListingsApi
) {
    suspend fun getListings() = api.getListings()
}
