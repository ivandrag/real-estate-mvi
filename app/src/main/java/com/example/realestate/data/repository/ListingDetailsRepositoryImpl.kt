package com.example.realestate.data.repository

import com.example.realestate.data.mapper.AllListingsMapper
import com.example.realestate.data.remote.ListingDetailsRemoteDataSource
import com.example.realestate.domain.model.ListingBO
import com.example.realestate.domain.repository.ListingDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.realestate.domain.utils.Result

class ListingDetailsRepositoryImpl(
    private val remoteDataSource: ListingDetailsRemoteDataSource,
    private val mapper: AllListingsMapper
) : ListingDetailsRepository {

    override suspend fun getListingDetails(id: Int): Flow<Result<ListingBO>> {
        return flow {
            emit(Result.Loading)
            try {
                val response = remoteDataSource.getListingDetails(id)
                emit(Result.Success(mapper.toListingBO(response)))
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }
    }
}