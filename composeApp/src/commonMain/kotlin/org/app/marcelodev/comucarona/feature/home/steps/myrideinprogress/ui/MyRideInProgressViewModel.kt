package org.app.marcelodev.comucarona.feature.home.steps.myrideinprogress.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.navigator.Navigator
import org.app.marcelodev.comucarona.feature.home.steps.myrideinprogress.domain.GetMyRideInProgressUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.app.marcelodev.comucarona.commons.usecase.LogoutUseCase
import org.app.marcelodev.comucarona.commons.utils.NavigationUtils
import org.app.marcelodev.comucarona.feature.home.steps.myrideinprogress.ui.MyRideInProgressViewModelEventState.*
import org.app.marcelodev.comucarona.feature.home.steps.rideinprogress.data.models.RideInProgressFilterOptions
import org.app.marcelodev.comucarona.feature.home.steps.rideinprogress.data.models.RideInProgressModel
import org.app.marcelodev.comucarona.feature.myrideinprogressdetails.ui.MyRideInProgressDetailsRoute
import org.app.marcelodev.comucarona.service.ktor.extensions.handleHttpException
import org.koin.core.component.KoinComponent

class MyRideInProgressViewModel(
    private val navigator: Navigator,
    private val getMyRideInProgressUseCase: GetMyRideInProgressUseCase,
    private val logoutUseCase: LogoutUseCase
) : ScreenModel, ViewModel(), KoinComponent {
    private val viewModelState =
        MutableStateFlow(
            MyRideInProgressViewModelState(
                rideInProgressFilterSelected = RideInProgressFilterOptions.ALL
            )
        )

    init {
        onLoadAvailableCarRide()
    }

    val uiState = viewModelState
        .map(MyRideInProgressViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    fun onEvent(event: MyRideInProgressViewModelEventState) {
        when (event) {
            is OnLoadMyRideInProgress -> onLoadAvailableCarRide()
            is OnSelectFilter -> onSelectFilter(event.rideInProgressFilterOptions)
            is OnNavigateToMyRideDetails -> onNavigateTo(event.riderId)
        }
    }

    private fun onLoadAvailableCarRide() {
        onUpdateLoading(true)

        viewModelScope.launch {
            val status = viewModelState.value.rideInProgressFilterSelected.toString()

            onUpdateLoading(false)

            getMyRideInProgressUseCase.invoke(status)
                .onSuccess { result ->
                    onUpdateRideInProgressList(result)
                    onUpdateLoading(false)
                }
                .onFailure { throwable ->
                    throwable.handleHttpException(
                        onUnauthorized = {
                            logoutUseCase(navigator)
                        },
                        others = {
                            onUpdateError(true)
                            onUpdateLoading(false)
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
                screen = MyRideInProgressDetailsRoute(riderId)
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

    private fun onUpdateLoading(isLoading: Boolean) {
        viewModelState.update { it.copy(isLoading = isLoading) }
    }

    private fun onUpdateError(error: Boolean) {
        viewModelState.update { it.copy(isError = error) }
    }
}