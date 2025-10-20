package com.example.presentation.listingDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.ListingDetailsRepository
import com.example.domain.utils.Result
import com.example.presentation.shared.model.Listing
import com.example.presentation.shared.model.toListingUI
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.Int

class ListingDetailsViewModel(
    private val listingDetailsRepository: ListingDetailsRepository
) : ViewModel() {

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    fun handleIntent(intent: ListingDetailsIntent) {
        viewModelScope.launch {
            when (intent) {
                ListingDetailsIntent.GoBack -> _event.emit(Event.GoBack)
                is ListingDetailsIntent.LoadListingDetails -> getListingDetails(intent.id)
            }
        }
    }

    private fun getListingDetails(id: Int) {
        viewModelScope.launch {
            listingDetailsRepository.getListingDetails(id).collect { result ->
                _state.update { prev ->
                    when (result) {
                        is Result.Success ->
                            prev.copy(
                                isLoading = false,
                                listing = result.data.toListingUI(),
                                errorMessage = ""
                            )

                        is Result.Error ->
                            prev.copy(
                                isLoading = false,
                                errorMessage = result.exception.message
                                    ?: "Unknown error when retrieving listing details"
                            )
                    }
                }
            }
        }
    }
}

data class State(
    val isLoading: Boolean = true,
    val listing: Listing = Listing(
        -1,
        "",
        "",
        "",
        0,
        0,
        null,
        "",
        0
    ),
    val errorMessage: String = ""
)

sealed class Event {
    data object GoBack : Event()
}
