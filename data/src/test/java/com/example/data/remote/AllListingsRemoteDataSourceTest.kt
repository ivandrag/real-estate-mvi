package com.example.data.remote

import com.example.data.model.AllListingsResponseDto
import com.example.data.networking.ListingsApi
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class AllListingsRemoteDataSourceTest {

    private val api = mockk<ListingsApi>(relaxed = true)
    private val dataSource = AllListingsRemoteDataSource(api)

    @Test
    fun `getListings calls api once`() = runTest {
        dataSource.getListings()

        coVerify(exactly = 1) { api.getListings() }
    }
}
