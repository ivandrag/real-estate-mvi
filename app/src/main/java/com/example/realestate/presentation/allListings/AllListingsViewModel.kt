package com.example.realestate.presentation.allListings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.realestate.domain.repository.AllListingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.realestate.domain.utils.Result
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

class AllListingsViewModel(
    private val repository: AllListingsRepository
) : ViewModel() {

    private val _event = MutableSharedFlow<Event>(replay = 1)
    val event = _event.asSharedFlow()
    private val _state = MutableStateFlow<State>(State.Loading)
    val state: StateFlow<State> = _state.asStateFlow()

    fun handleIntent(intent: AllListingsIntent)  {
        viewModelScope.launch {
            when (intent) {
                AllListingsIntent.GetAllListings -> getAllListings()
                is AllListingsIntent.NavigateToListingDetails -> _event.emit(Event.NavigateToListingDetails(
                    intent.id
                ))
            }
        }
    }

    private fun getAllListings() {
        viewModelScope.launch {
            repository.getListings().collect { result ->
                _state.value = when (result) {
                    is Result.Loading -> State.Loading
                    is Result.Success -> State.Success(result.data.toUI())
                    is Result.Error -> State.Error(
                        result.exception.message
                            ?: "Unknown error occurred when retrieving all listings"
                    )
                }
            }
        }
    }
}

sealed class State {
    data object Loading : State()
    data class Success(val listings: List<Listing>) : State()
    data class Error(val message: String) : State()
}

sealed class Event {
    data class NavigateToListingDetails(val id: Int): Event()
}
