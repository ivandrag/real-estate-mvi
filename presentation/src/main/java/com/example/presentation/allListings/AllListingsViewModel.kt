package com.example.presentation.allListings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.AllListingsRepository
import com.example.domain.utils.Result
import com.example.presentation.allListings.model.toListingUiList
import com.example.presentation.shared.model.Listing
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AllListingsViewModel(
    private val repository: AllListingsRepository
) : ViewModel() {

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()
    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()

    fun handleIntent(intent: AllListingsIntent) {
        viewModelScope.launch {
            when (intent) {
                AllListingsIntent.GetAllListings -> getAllListings()
                is AllListingsIntent.NavigateToListingDetails -> _event.emit(
                    Event.NavigateToListingDetails(
                        intent.id
                    )
                )
            }
        }
    }

    private fun getAllListings() {
        viewModelScope.launch {
            repository.getListings().collect { result ->
                _state.update { prev ->
                    when (result) {
                        is Result.Success -> prev.copy(
                            isLoading = false,
                            listings = result.data.toListingUiList(),
                            errorMessage = ""
                        )

                        is Result.Error -> prev.copy(
                            isLoading = false,
                            errorMessage = result.exception.message ?: "Unknown error"
                        )
                    }
                }
            }
        }
    }
}

data class State(
    val isLoading: Boolean = true,
    val listings: List<Listing> = emptyList(),
    val errorMessage: String = ""
)

sealed class Event {
    data class NavigateToListingDetails(val id: Int) : Event()
}
