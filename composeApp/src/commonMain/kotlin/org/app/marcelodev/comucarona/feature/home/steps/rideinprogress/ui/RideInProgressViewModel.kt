package org.app.marcelodev.comucarona.feature.home.steps.rideinprogress.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.navigator.Navigator
import org.app.marcelodev.comucarona.feature.home.steps.rideinprogress.data.models.RideInProgressFilterOptions
import org.app.marcelodev.comucarona.feature.home.steps.rideinprogress.data.models.RideInProgressModel
import org.app.marcelodev.comucarona.feature.home.steps.rideinprogress.domain.GetRideInProgressUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.app.marcelodev.comucarona.commons.usecase.LogoutUseCase
import org.app.marcelodev.comucarona.commons.utils.NavigationUtils
import org.app.marcelodev.comucarona.feature.home.steps.rideinprogress.ui.RideInProgressViewModelEventState.*
import org.app.marcelodev.comucarona.feature.rideinprogressDetails.ui.RideInProgressDetailsRoute
import org.app.marcelodev.comucarona.service.ktor.extensions.handleHttpException
import org.koin.core.component.KoinComponent

class RideInProgressViewModel(
    private val navigator: Navigator,
    private val getRideInProgressUseCase: GetRideInProgressUseCase,
    private val logoutUseCase: LogoutUseCase
) : ScreenModel, ViewModel(), KoinComponent {
    private val viewModelState =
        MutableStateFlow(
            RideInProgressViewModelState(
                rideInProgressFilterSelected = RideInProgressFilterOptions.ALL
            )
        )

    init {
        onLoadAvailableCarRide()
    }

    val uiState = viewModelState
        .map(RideInProgressViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    fun onEvent(event: RideInProgressViewModelEventState) {
        when (event) {
            is OnLoadRideInProgress -> onLoadAvailableCarRide()
            is OnSelectFilter -> onSelectFilter(event.rideInProgressFilterOptions)
            is OnNavigateToRideDetails -> onNavigateTo(event.riderId)
        }
    }

    private fun onLoadAvailableCarRide() {
        onUpdateLoading(true)
        onUpdateIsRefreshing(true)

        viewModelScope.launch {
            val status = viewModelState.value.rideInProgressFilterSelected.toString()

            getRideInProgressUseCase.invoke(status)
                .onSuccess { result ->
                    onUpdateRideInProgressList(result)
                    onUpdateLoading(false)
                    onUpdateIsRefreshing(false)
                }
                .onFailure { throwable ->
                    throwable.handleHttpException(
                        onUnauthorized = {
                            logoutUseCase(navigator)
                        },
                        others = {
                            onUpdateError(true)
                            onUpdateLoading(false)
                            onUpdateIsRefreshing(false)
                        }
                    )
                }
        }
    }

    private fun onSelectFilter(rideInProgressFilterOptions: RideInProgressFilterOptions) {
        onUpdateFilterSelected(rideInProgressFilterOptions)
        onLoadAvailableCarRide()
    }

    private fun onNavigateTo(riderId: String) {
        navigator.parent?.let {
            NavigationUtils.addNewScreen(
                navigator = it,
                screen = RideInProgressDetailsRoute(riderId)
            )
        }
    }

    private fun onUpdateRideInProgressList(rideInProgressList: List<RideInProgressModel>) {
        viewModelState.update {
            it.copy(
                rideInProgressList = rideInProgressList,
                rideInProgressListFiltered = rideInProgressList
            )
        }
    }

    private fun onUpdateFilterSelected(rideInProgressFilterOptions: RideInProgressFilterOptions) {
        viewModelState.update {
            it.copy(rideInProgressFilterSelected = rideInProgressFilterOptions)
        }
    }

    private fun onUpdateIsRefreshing(isRefreshing: Boolean) {
        viewModelState.update { it.copy(isRefresh = isRefreshing) }
    }

    private fun onUpdateLoading(isLoading: Boolean) {
        viewModelState.update { it.copy(isLoading = isLoading) }
    }

    private fun onUpdateError(error: Boolean) {
        viewModelState.update { it.copy(isError = error) }
    }
}