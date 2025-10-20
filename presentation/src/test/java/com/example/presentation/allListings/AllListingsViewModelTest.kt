package com.example.presentation.allListings

import app.cash.turbine.test
import com.example.domain.model.ListingBO
import com.example.domain.repository.AllListingsRepository
import com.example.presentation.utils.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.example.domain.utils.Result
import com.example.presentation.utils.formatPrice
import kotlinx.coroutines.flow.flow
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

class AllListingsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository = mockk<AllListingsRepository>()
    private lateinit var viewModel: AllListingsViewModel

    @Before
    fun setUp() {
        viewModel = AllListingsViewModel(repository)
    }

    @Test
    fun `handleIntent with GetAllListings emits Success with mapped UI list`() = runTest {
        val listingBO = ListingBO(
            id = 1,
            city = "Bucharest",
            propertyType = "Apartment",
            price = 350.0,
            bedrooms = 2,
            area = 55.0,
            imageUrl = "https://img.example.com/1.jpg",
            professional = "GSL Owners",
            rooms = 3
        )

        coEvery { repository.getListings() } returns flow {
            emit(Result.Success(listOf(listingBO)))
        }

        viewModel.handleIntent(AllListingsIntent.GetAllListings)

        val state = viewModel.state.value
        assertTrue(state.listings.isNotEmpty())

        assertEquals(1, state.listings.size)
        val ui = state.listings.first()
        assertEquals(listingBO.id, ui.id)
        assertEquals(listingBO.city, ui.city)
        assertEquals(listingBO.propertyType, ui.propertyType)
        assertEquals(listingBO.price.formatPrice(), ui.price)
        // Default US Locale Pricing Test
        assertEquals("$350", ui.price)
        assertEquals(listingBO.bedrooms, ui.bedrooms)
        assertEquals(listingBO.area.toInt(), ui.area)
        assertEquals(listingBO.imageUrl, ui.imageUrl)
        assertEquals(listingBO.professional, ui.professional)
        assertEquals(listingBO.rooms, ui.rooms)
    }

    @Test
    fun `handleIntent with GetAllListings emits Error`() = runTest {
        val ex = IllegalStateException("error")
        coEvery { repository.getListings() } returns flow {
            emit(Result.Error(ex))
        }

        viewModel.handleIntent(AllListingsIntent.GetAllListings)

        val state = viewModel.state.value
        assertTrue(state.errorMessage.isNotEmpty())
        assertTrue((state.errorMessage.contains("error")))
    }

    @Test
    fun `handleIntent with NavigateToListingDetails emits event`() = runTest {
        val id = 42

        viewModel.event.test {
            viewModel.handleIntent(AllListingsIntent.NavigateToListingDetails(id))
            val event = awaitItem()
            assertTrue(event is Event.NavigateToListingDetails)
            assertEquals(id, (event as Event.NavigateToListingDetails).id)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
