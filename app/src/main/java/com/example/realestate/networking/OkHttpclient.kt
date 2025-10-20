package com.example.realestate.networking

import com.example.realestate.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

fun provideJson(): Json = Json {
    ignoreUnknownKeys = true
    coerceInputValues = true
    isLenient = true
    encodeDefaults = true
}

fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
    HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

fun provideOkHttpClient(
    httpLoggingInterceptor: HttpLoggingInterceptor
): OkHttpClient {
    return OkHttpClient.Builder().apply {
        connectTimeout(ApiConstants.CONNECT_TIMEOUT, TimeUnit.SECONDS)
        readTimeout(ApiConstants.READ_TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(ApiConstants.WRITE_TIMEOUT, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            addInterceptor(httpLoggingInterceptor)
        }
    }.build()
}

@OptIn(ExperimentalSerializationApi::class)
fun provideRetrofit(
    okHttpClient: OkHttpClient,
    json: Json
): Retrofit {
    val contentType = "application/json".toMediaType()
    return Retrofit.Builder()
        .baseUrl(ApiConstants.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()
}

fun provideListingsApi(retrofit: Retrofit): ListingsApi {
    return retrofit.create(ListingsApi::class.java)
}
