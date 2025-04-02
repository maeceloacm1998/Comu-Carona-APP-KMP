package org.app.marcelodev.comucarona.feature.home.steps.myrideinprogress.ui

import org.app.marcelodev.comucarona.feature.home.steps.rideinprogress.data.models.RideInProgressFilterOptions


sealed class MyRideInProgressViewModelEventState {
    data object OnLoadMyRideInProgress : MyRideInProgressViewModelEventState()
    data object OnUpdateMyRideInProgressLiSt: MyRideInProgressViewModelEventState()
    data class OnSelectFilter(val rideInProgressFilterOptions: RideInProgressFilterOptions) :
        MyRideInProgressViewModelEventState()

    data class OnNavigateToMyRideDetails(val riderId: String) :
        MyRideInProgressViewModelEventState()
}