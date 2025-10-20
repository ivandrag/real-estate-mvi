package com.example.realestate.di

import org.koin.dsl.module
import com.example.realestate.presentation.allListings.AllListingsViewModel
import org.koin.core.module.dsl.viewModel

val viewModelModule = module {
    viewModel { AllListingsViewModel(get()) }
}
