package com.example.data.repository

import app.cash.turbine.test
import com.example.data.mapper.AllListingsMapper
import com.example.data.model.ListingResponseDto
import com.example.data.remote.ListingDetailsRemoteDataSource
import com.example.domain.model.ListingBO
import com.example.domain.utils.Result
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ListingDetailsRepositoryImplTest {

    private val remote = mockk<ListingDetailsRemoteDataSource>()
    private val mapper = mockk<AllListingsMapper>()
    private val repo = ListingDetailsRepositoryImpl(remote, mapper)

    @Test
    fun `getListingDetails emits Success when API succeeds`() = runTest {
        val id = 42
        val dto = ListingResponseDto(
            id = id,
            city = "Bucharest",
            propertyType = "Apartment",
            price = 350.0,
            bedrooms = 2,
            area = 55.0,
            url = "u",
            professional = "GSL",
            offerType = 1,
            rooms = 3
        )
        val bo = mockk<ListingBO>()
        coEvery { remote.getListingDetails(id) } returns dto
        every { mapper.toListingBO(dto) } returns bo

        repo.getListingDetails(id).test {
            val success = awaitItem() as Result.Success
            assertEquals(bo, success.data)
            awaitComplete()
        }
    }

    @Test
    fun `getListingDetails emits Error when API throws`() = runTest {
        val id = 7
        val exception = IllegalStateException("error")
        coEvery { remote.getListingDetails(id) } throws exception

        repo.getListingDetails(id).test {
            val error = awaitItem() as Result.Error
            assertTrue(error.exception is IllegalStateException)
            assertEquals("error", error.exception.message)
            awaitComplete()
        }
    }
}
