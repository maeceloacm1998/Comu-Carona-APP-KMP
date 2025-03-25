package org.app.marcelodev.comucarona.feature.home.steps.initial.ui

sealed class InitialViewModelEventState {
    data object OnLoadAvailableCarRide : InitialViewModelEventState()
    data class OnNavigateToRideDetails(val id: String = "") : InitialViewModelEventState()
    data object OnNavigateToCreateCarRide : InitialViewModelEventState()
}