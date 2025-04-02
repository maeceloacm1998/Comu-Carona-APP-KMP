package org.app.marcelodev.comucarona.feature.home.steps.myrideinprogress.ui

import org.app.marcelodev.comucarona.feature.home.steps.rideinprogress.data.models.RideInProgressFilterOptions
import org.app.marcelodev.comucarona.feature.home.steps.rideinprogress.data.models.RideInProgressModel

sealed interface MyRideInProgressViewModelUiState {
    val isLoading: Boolean
    val isError: Boolean
    val isRefresh: Boolean
    val rideInProgressFilterSelected: RideInProgressFilterOptions

    /**
     * Represents the state when the home screen is loading.
     */
    data class NoHasRiderInProgress(
        override val rideInProgressFilterSelected: RideInProgressFilterOptions,
        override val isLoading: Boolean,
        override val isError: Boolean,
        override val isRefresh: Boolean,
    ) : MyRideInProgressViewModelUiState


    /**
     * Represents the state when the home screen is loading.
     */
    data class HasRiderInProgress(
        val rideInProgressList: List<RideInProgressModel>,
        val rideInProgressListFiltered: List<RideInProgressModel>,
        val isLoadingList: Boolean,
        override val rideInProgressFilterSelected: RideInProgressFilterOptions,
        override val isLoading: Boolean,
        override val isError: Boolean,
        override val isRefresh: Boolean,
    ) : MyRideInProgressViewModelUiState
}

/**
 * Represents the state of the home screen.
 */
data class MyRideInProgressViewModelState(
    val rideInProgressList: List<RideInProgressModel> = emptyList(),
    val rideInProgressListFiltered: List<RideInProgressModel> = emptyList(),
    val rideInProgressFilterSelected: RideInProgressFilterOptions = RideInProgressFilterOptions.ALL,
    val isLoadingList: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isRefresh: Boolean = false,
) {

    /**
     * Converts the state to a UI state.
     */
    fun toUiState(): MyRideInProgressViewModelUiState = if (rideInProgressListFiltered.isNotEmpty()) {
        MyRideInProgressViewModelUiState.HasRiderInProgress(
            rideInProgressList = rideInProgressList,
            rideInProgressListFiltered = rideInProgressListFiltered,
            rideInProgressFilterSelected = rideInProgressFilterSelected,
            isLoadingList = isLoadingList,
            isLoading = isLoading,
            isError = isError,
            isRefresh = isRefresh,
        )
    } else {
        MyRideInProgressViewModelUiState.NoHasRiderInProgress(
            rideInProgressFilterSelected = rideInProgressFilterSelected,
            isLoading = isLoading,
            isError = isError,
            isRefresh = isRefresh,
        )
    }
}
