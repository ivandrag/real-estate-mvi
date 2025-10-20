package com.example.domain.repository

import com.example.domain.model.ListingBO
import com.example.domain.utils.Result
import kotlinx.coroutines.flow.Flow

interface AllListingsRepository {
    fun getListings(): Flow<Result<List<ListingBO>>>
}
