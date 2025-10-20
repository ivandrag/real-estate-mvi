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
import kotlinx.coroutines.launch

class ListingDetailsViewModel(
    private val listingDetailsRepository: ListingDetailsRepository
) : ViewModel() {

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    private val _state = MutableStateFlow<State>(State.Loading)
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
                _state.value = when (result) {
                    Result.Loading -> State.Loading
                    is Result.Error -> State.Error(
                        result.exception.message ?: "Unknown error when retrieving listing details"
                    )

                    is Result.Success -> State.Success(result.data.toListingUI())
                }
            }
        }
    }
}

sealed class State {
    data object Loading : State()
    data class Success(val listing: Listing) : State()
    data class Error(val message: String) : State()
}

sealed class Event {
    data object GoBack : Event()
}
