package com.example.data.repository

import com.example.data.mapper.AllListingsMapper
import com.example.data.remote.AllListingsRemoteDataSource
import com.example.domain.model.ListingBO
import com.example.domain.repository.AllListingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.domain.utils.Result

class AllListingsRepositoryImpl(
    private val remoteDataSource: AllListingsRemoteDataSource,
    private val mapper: AllListingsMapper
) : AllListingsRepository {

    override fun getListings(): Flow<Result<List<ListingBO>>> =
        flow {
            emit(Result.Loading)
            try {
                val response = remoteDataSource.getListings()
                val listings = mapper.toAllListings(response.items)
                emit(Result.Success(listings))
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }
}
