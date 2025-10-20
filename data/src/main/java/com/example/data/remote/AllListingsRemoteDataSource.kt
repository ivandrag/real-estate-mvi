package com.example.data.remote

import com.example.data.networking.ListingsApi

class AllListingsRemoteDataSource(
    private val api: ListingsApi
) {
    suspend fun getListings() = api.getListings()
}
