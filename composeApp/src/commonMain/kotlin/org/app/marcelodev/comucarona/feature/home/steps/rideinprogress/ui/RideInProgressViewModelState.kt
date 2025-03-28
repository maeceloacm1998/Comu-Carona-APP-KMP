package org.app.marcelodev.comucarona.feature.home.steps.rideinprogress.ui

import org.app.marcelodev.comucarona.feature.home.steps.rideinprogress.data.models.RideInProgressFilterOptions
import org.app.marcelodev.comucarona.feature.home.steps.rideinprogress.data.models.RideInProgressModel

sealed interface RideInProgressViewModelUiState {
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
    ) : RideInProgressViewModelUiState


    /**
     * Represents the state when the home screen is loading.
     */
    data class HasRiderInProgress(
        val rideInProgressList: List<RideInProgressModel>,
        val rideInProgressListFiltered: List<RideInProgressModel>,
        override val rideInProgressFilterSelected: RideInProgressFilterOptions,
        override val isLoading: Boolean,
        override val isError: Boolean,
        override val isRefresh: Boolean,
    ) : RideInProgressViewModelUiState
}

/**
 * Represents the state of the home screen.
 */
data class RideInProgressViewModelState(
    val rideInProgressList: List<RideInProgressModel> = emptyList(),
    val rideInProgressListFiltered: List<RideInProgressModel> = emptyList(),
    val rideInProgressFilterSelected: RideInProgressFilterOptions = RideInProgressFilterOptions.ALL,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isRefresh: Boolean = false,
) {

    /**
     * Converts the state to a UI state.
     */
    fun toUiState(): RideInProgressViewModelUiState = if (rideInProgressListFiltered.isNotEmpty()) {
        RideInProgressViewModelUiState.HasRiderInProgress(
            rideInProgressList = rideInProgressList,
            rideInProgressListFiltered = rideInProgressListFiltered,
            rideInProgressFilterSelected = rideInProgressFilterSelected,
            isLoading = isLoading,
            isError = isError,
            isRefresh = isRefresh,
        )
    } else {
        RideInProgressViewModelUiState.NoHasRiderInProgress(
            rideInProgressFilterSelected = rideInProgressFilterSelected,
            isLoading = isLoading,
            isError = isError,
            isRefresh = isRefresh,
        )
    }
}
