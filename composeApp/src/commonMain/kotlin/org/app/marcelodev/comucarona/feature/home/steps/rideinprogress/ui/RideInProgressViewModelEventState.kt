package org.app.marcelodev.comucarona.feature.home.steps.rideinprogress.ui

import org.app.marcelodev.comucarona.feature.home.steps.rideinprogress.data.models.RideInProgressFilterOptions

sealed class RideInProgressViewModelEventState {
    data object OnLoadRideInProgress : RideInProgressViewModelEventState()
    data class OnSelectFilter(val rideInProgressFilterOptions: RideInProgressFilterOptions) :
        RideInProgressViewModelEventState()

    data class OnNavigateToRideDetails(val riderId: String) :
        RideInProgressViewModelEventState()
}