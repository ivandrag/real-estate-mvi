package com.example.presentation.listingDetails

import app.cash.turbine.test
import com.example.domain.model.ListingBO
import com.example.domain.repository.ListingDetailsRepository
import com.example.domain.utils.Result
import com.example.presentation.utils.MainDispatcherRule
import com.example.presentation.utils.formatPrice
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ListingDetailsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val listingDetailsRepository = mockk<ListingDetailsRepository>()
    private lateinit var viewModel: ListingDetailsViewModel

    @Before
    fun setUp() {
        viewModel = ListingDetailsViewModel(listingDetailsRepository)
    }

    @Test
    fun `handleIntent with LoadListingDetails emits Loading`() = runTest {
        val id = 1
        coEvery { listingDetailsRepository.getListingDetails(id) } returns flowOf(Result.Loading)

        viewModel.handleIntent(ListingDetailsIntent.LoadListingDetails(id))

        assertTrue(viewModel.state.value is State.Loading)
    }

    @Test
    fun `handleIntent with LoadListingDetails emits Success with mapped UI`() = runTest {
        val id = 7
        val bo = ListingBO(
            id = id,
            city = "Bucharest",
            propertyType = "Apartment",
            price = 3500.0,
            bedrooms = 2,
            area = 55.0,
            imageUrl = "https://img/1.jpg",
            professional = "GSL Owners",
            rooms = 3
        )
        coEvery { listingDetailsRepository.getListingDetails(id) } returns flow {
            emit(Result.Loading)
            emit(Result.Success(bo))
        }

        viewModel.handleIntent(ListingDetailsIntent.LoadListingDetails(id))

        val state = viewModel.state.value
        assertTrue(state is State.Success)
        state as State.Success

        val ui = state.listing
        assertEquals(bo.id, ui.id)
        assertEquals(bo.city, ui.city)
        assertEquals(bo.propertyType, ui.propertyType)
        assertEquals(bo.price.formatPrice(), ui.price)
        assertEquals("$3,500", ui.price)
        assertEquals(bo.bedrooms, ui.bedrooms)
        assertEquals(bo.area.toInt(), ui.area)
        assertEquals(bo.imageUrl, ui.imageUrl)
        assertEquals(bo.professional, ui.professional)
        assertEquals(bo.rooms, ui.rooms)
    }

    @Test
    fun `handleIntent with LoadListingDetails emits Error`() = runTest {
        val id = 3
        val ex = IllegalStateException("boom")
        coEvery { listingDetailsRepository.getListingDetails(id) } returns flow {
            emit(Result.Loading)
            emit(Result.Error(ex))
        }

        viewModel.handleIntent(ListingDetailsIntent.LoadListingDetails(id))

        val state = viewModel.state.value
        assertTrue(state is State.Error)
        assertTrue((state as State.Error).message.contains("boom"))
    }

    @Test
    fun `handleIntent with GoBack emits event`() = runTest {
        viewModel.event.test {
            viewModel.handleIntent(ListingDetailsIntent.GoBack)

            val ev = awaitItem()
            assertTrue(ev is Event.GoBack)

            cancelAndIgnoreRemainingEvents()
        }
    }
}
