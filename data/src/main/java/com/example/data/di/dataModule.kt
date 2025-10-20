package com.example.data.di

import com.example.data.mapper.AllListingsMapper
import com.example.data.remote.AllListingsRemoteDataSource
import com.example.data.remote.ListingDetailsRemoteDataSource
import com.example.data.repository.AllListingsRepositoryImpl
import com.example.data.repository.ListingDetailsRepositoryImpl
import com.example.domain.repository.AllListingsRepository
import com.example.domain.repository.ListingDetailsRepository
import org.koin.dsl.module

val dataModule = module {
    factory { AllListingsMapper() }
    factory { AllListingsRemoteDataSource(get()) }
    factory { ListingDetailsRemoteDataSource(get()) }
    factory<AllListingsRepository> { AllListingsRepositoryImpl(get(), get()) }
    factory<ListingDetailsRepository> { ListingDetailsRepositoryImpl(get(), get()) }
}
