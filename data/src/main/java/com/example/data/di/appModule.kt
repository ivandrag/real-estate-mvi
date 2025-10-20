package com.example.data.di

import com.example.data.networking.provideHttpLoggingInterceptor
import com.example.data.networking.provideJson
import com.example.data.networking.provideListingsApi
import com.example.data.networking.provideOkHttpClient
import com.example.data.networking.provideRetrofit
import org.koin.dsl.module

val appModule = module {
    single { provideJson() }
    single { provideHttpLoggingInterceptor() }
    single { provideOkHttpClient(get()) }
    single { provideRetrofit(get(), get()) }
    single { provideListingsApi(get()) }
}
