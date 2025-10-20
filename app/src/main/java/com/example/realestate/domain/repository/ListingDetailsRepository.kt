package com.example.realestate.domain.repository

import com.example.realestate.domain.model.ListingBO
import com.example.realestate.domain.utils.Result
import kotlinx.coroutines.flow.Flow

interface ListingDetailsRepository {
    suspend fun getListingDetails(id: Int): Flow<Result<ListingBO>>
}
