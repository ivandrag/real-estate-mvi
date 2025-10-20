package com.example.presentation.di

import com.example.presentation.allListings.AllListingsViewModel
import com.example.presentation.listingDetails.ListingDetailsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { AllListingsViewModel(get()) }
    viewModel { ListingDetailsViewModel(get()) }
}
