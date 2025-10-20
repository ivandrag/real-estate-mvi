package com.example.realestate.di

import com.example.realestate.networking.provideHttpLoggingInterceptor
import com.example.realestate.networking.provideJson
import com.example.realestate.networking.provideListingsApi
import com.example.realestate.networking.provideOkHttpClient
import com.example.realestate.networking.provideRetrofit
import org.koin.dsl.module

val appModule = module {
    single { provideJson() }
    single { provideHttpLoggingInterceptor() }
    single { provideOkHttpClient(get()) }
    single { provideRetrofit(get(), get()) }
    single { provideListingsApi(get()) }
}
