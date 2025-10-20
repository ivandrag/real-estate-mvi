package com.example.realestate.domain.repository

import com.example.realestate.domain.model.ListingBO
import com.example.realestate.domain.utils.Result
import kotlinx.coroutines.flow.Flow

interface AllListingsRepository {
    fun getListings(): Flow<Result<List<ListingBO>>>
}
