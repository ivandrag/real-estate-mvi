package com.example.data.remote

import com.example.data.networking.ListingsApi
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ListingDetailsRemoteDataSourceTest {
    private val api = mockk<ListingsApi>(relaxed = true)
    private val dataSource = ListingDetailsRemoteDataSource(api)

    @Test
    fun `getListings calls api once`() = runTest {
        dataSource.getListingDetails(0)

        coVerify(exactly = 1) { api.getListingDetail(0) }
    }
}
