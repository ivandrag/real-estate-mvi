package com.example.data.repository

import com.example.data.mapper.AllListingsMapper
import com.example.data.remote.ListingDetailsRemoteDataSource
import com.example.domain.model.ListingBO
import com.example.domain.repository.ListingDetailsRepository
import com.example.domain.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

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
