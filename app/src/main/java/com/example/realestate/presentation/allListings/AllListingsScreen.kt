package com.example.realestate.presentation.allListings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import com.example.realestate.R
import org.koin.androidx.compose.koinViewModel
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.example.realestate.presentation.shared.model.Listing
import com.example.realestate.presentation.shared.EmptyView
import com.example.realestate.presentation.shared.ErrorView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllListingsScreen(
    viewModel: AllListingsViewModel = koinViewModel(),
    onNavigateToListingDetails: (Int) -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.handleIntent(AllListingsIntent.GetAllListings)
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(viewModel.event, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.STARTED) {
            viewModel.event.collect { event ->
                when (event) {
                    is Event.NavigateToListingDetails -> onNavigateToListingDetails(event.id)
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.all_listings_title_text))
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (state) {
                is State.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is State.Success -> {
                    val listings = (state as State.Success).listings
                    if (listings.isEmpty()) {
                        EmptyView()
                    } else {
                        ListingsContent(
                            listings = listings,
                            onListingClick = { id ->
                                viewModel.handleIntent(
                                    AllListingsIntent.NavigateToListingDetails(
                                        id
                                    )
                                )
                            }
                        )
                    }
                }

                is State.Error -> {
                    ErrorView(
                        message = (state as State.Error).message,
                        onRetry = { viewModel.handleIntent(AllListingsIntent.GetAllListings) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ListingsContent(
    listings: List<Listing>,
    onListingClick: (Int) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(
            dimensionResource(
                R.dimen.padding_16
            )
        ),
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(
                R.dimen.padding_8
            )
        )
    ) {
        items(
            items = listings,
            key = { it.id }
        ) { listing ->
            ListingCard(
                listing = listing,
                onClick = { onListingClick(listing.id) }
            )
        }
    }
}

@Composable
private fun ListingCard(
    listing: Listing,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_8))
        ) {
            AsyncImage(
                model = listing.imageUrl,
                contentDescription = stringResource(R.string.all_listings_image_content_description),
                modifier = Modifier.size(dimensionResource(R.dimen.all_listings_image_size)),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.ic_default_listings_image),
                error = painterResource(R.drawable.ic_default_listings_image)
            )

            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_16)))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = listing.propertyType,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_4)))

                Text(
                    text = listing.city,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_8)))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        dimensionResource(R.dimen.padding_8)
                    )
                ) {
                    PropertyDetail(
                        label = if (listing.bedrooms == 0) {
                            stringResource(R.string.not_available_text)
                        } else {
                            pluralStringResource(
                                R.plurals.all_listings_beds,
                                listing.bedrooms,
                                listing.bedrooms
                            )
                        }
                    )
                    PropertyDetail(
                        label = if (listing.rooms == 0) {
                            stringResource(R.string.not_available_text)
                        } else {
                            pluralStringResource(
                                R.plurals.all_listings_rooms,
                                listing.rooms,
                                listing.rooms
                            )
                        }
                    )
                    PropertyDetail(
                        label = if (listing.area == 0) {
                            stringResource(R.string.not_available_text)
                        } else {
                            stringResource(R.string.all_listings_area, listing.area)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_8)))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = listing.price,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
private fun PropertyDetail(label: String) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(
                horizontal = dimensionResource(
                    R.dimen.padding_8
                ), vertical = dimensionResource(
                    R.dimen.padding_4
                )
            ),
            style = MaterialTheme.typography.bodySmall
        )
    }
}
