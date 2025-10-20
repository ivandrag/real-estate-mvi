package com.example.realestate.data.repository

import com.example.realestate.data.mapper.AllListingsMapper
import com.example.realestate.data.remote.AllListingsRemoteDataSource
import com.example.realestate.domain.model.ListingBO
import com.example.realestate.domain.repository.AllListingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class AllListingsRepositoryImpl(
    private val remoteDataSource: AllListingsRemoteDataSource,
    private val mapper: AllListingsMapper
) : AllListingsRepository {

    override fun getListings(): Flow<com.example.realestate.domain.utils.Result<List<ListingBO>>> =
        flow {
            emit(com.example.realestate.domain.utils.Result.Loading)
            try {
                val response = remoteDataSource.getListings()
                val listings = mapper.toAllListings(response.items)
                emit(com.example.realestate.domain.utils.Result.Success(listings))
            } catch (e: Exception) {
                emit(com.example.realestate.domain.utils.Result.Error(e))
            }
        }
}
