package com.example.realestate.di

import com.example.realestate.data.mapper.AllListingsMapper
import com.example.realestate.data.remote.AllListingsRemoteDataSource
import com.example.realestate.data.repository.AllListingsRepositoryImpl
import com.example.realestate.domain.repository.AllListingsRepository
import org.koin.dsl.module

val dataModule = module {
    factory { AllListingsMapper() }
    factory { AllListingsRemoteDataSource(get()) }
    factory<AllListingsRepository> { AllListingsRepositoryImpl(get(), get()) }
}