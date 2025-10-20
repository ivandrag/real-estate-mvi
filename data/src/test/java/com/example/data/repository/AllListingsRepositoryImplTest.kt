package com.example.data.repository

import app.cash.turbine.test
import com.example.data.mapper.AllListingsMapper
import com.example.data.model.AllListingsResponseDto
import com.example.data.model.ListingResponseDto
import com.example.data.remote.AllListingsRemoteDataSource
import com.example.domain.model.ListingBO
import com.example.domain.utils.Result
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AllListingsRepositoryImplTest {

    private val remoteDataSource = mockk<AllListingsRemoteDataSource>()
    private val mapper = mockk<AllListingsMapper>()
    private val repository = AllListingsRepositoryImpl(remoteDataSource, mapper)

    @Test
    fun `getListings emits Loading then Success when API call succeeds`() = runTest {
        val dtoList = listOf(
            ListingResponseDto(
                id = 1,
                city = "Cluj",
                propertyType = "Apartment",
                price = 100.0,
                bedrooms = 2,
                area = 60.0,
                url = "u1",
                professional = "GSL",
                offerType = 1,
                rooms = 3
            )
        )
        val response = AllListingsResponseDto(items = dtoList)
        val mapped = listOf(mockk<ListingBO>())

        coEvery { remoteDataSource.getListings() } returns response
        every { mapper.toAllListings(response.items) } returns mapped

        repository.getListings().test {
            assertEquals(Result.Loading, awaitItem())
            val success = awaitItem() as Result.Success
            assertEquals(mapped, success.data)
            awaitComplete()
        }
    }

    @Test
    fun `getListings emits Loading then Error when API call throws`() = runTest {
        val exception = RuntimeException("network error")
        coEvery { remoteDataSource.getListings() } throws exception

        repository.getListings().test {
            assertEquals(Result.Loading, awaitItem())
            val error = awaitItem() as Result.Error
            assertTrue(error.exception is RuntimeException)
            assertEquals("network error", error.exception.message)
            awaitComplete()
        }
    }
}
