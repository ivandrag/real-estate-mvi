package com.example.realestate.di

import com.example.realestate.data.mapper.AllListingsMapper
import com.example.realestate.data.remote.AllListingsRemoteDataSource
import com.example.realestate.data.remote.ListingDetailsRemoteDataSource
import com.example.realestate.data.repository.AllListingsRepositoryImpl
import com.example.realestate.data.repository.ListingDetailsRepositoryImpl
import com.example.realestate.domain.repository.AllListingsRepository
import com.example.realestate.domain.repository.ListingDetailsRepository
import org.koin.dsl.module

val dataModule = module {
    factory { AllListingsMapper() }
    factory { AllListingsRemoteDataSource(get()) }
    factory { ListingDetailsRemoteDataSource(get()) }
    factory<AllListingsRepository> { AllListingsRepositoryImpl(get(), get()) }
    factory<ListingDetailsRepository> { ListingDetailsRepositoryImpl(get(), get()) }
}
