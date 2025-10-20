package com.example.realestate.presentation.listingDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class ListingDetailsViewModel : ViewModel() {

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    fun handleIntent(intent: ListingDetailsIntent) {
        viewModelScope.launch {
            when (intent) {
                ListingDetailsIntent.GoBack -> _event.emit(Event.GoBack)
            }
        }
    }
}

sealed class Event {
    data object GoBack : Event()
}
