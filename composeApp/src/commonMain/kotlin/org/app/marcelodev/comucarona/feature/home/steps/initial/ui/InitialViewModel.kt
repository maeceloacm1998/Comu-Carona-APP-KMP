package org.app.marcelodev.comucarona.feature.home.steps.initial.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.navigator.Navigator
import org.app.marcelodev.comucarona.feature.home.steps.initial.data.models.AvailableCarRide
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.app.marcelodev.comucarona.commons.usecase.LogoutUseCase
import org.app.marcelodev.comucarona.commons.utils.NavigationUtils
import org.app.marcelodev.comucarona.feature.carridedetails.ui.CarRideDetailsRoute
import org.app.marcelodev.comucarona.feature.checkcode.ui.CheckCodeRoute
import org.app.marcelodev.comucarona.feature.home.steps.initial.domain.AvailableCarRidesUseCase
import org.app.marcelodev.comucarona.feature.home.steps.initial.domain.GetUserInformationUseCase
import org.app.marcelodev.comucarona.feature.home.steps.initial.ui.InitialViewModelEventState.*
import org.app.marcelodev.comucarona.service.ktor.extensions.handleHttpException
import org.koin.core.component.KoinComponent

class InitialViewModel(
    private val navigator: Navigator,
    private val availableCarRidesUseCase: AvailableCarRidesUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getUserInformationUseCase: GetUserInformationUseCase
) : ScreenModel, ViewModel(), KoinComponent {
    private val viewModelState = MutableStateFlow(InitialViewModelState())

    init {
        onLoadUserInformation()
        onLoadAvailableCarRide()
    }

    val uiState = viewModelState
        .map(InitialViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    init {
        onLoadUserInformation()
        onLoadAvailableCarRide()
    }

    fun onEvent(event: InitialViewModelEventState) {
        when (event) {
            is OnLoadAvailableCarRide -> onLoadAvailableCarRide()
            is OnNavigateToRideDetails -> onNavigateToRideDetails(event.id)
            is OnNavigateToCreateCarRide -> navigator.parent?.let {
                NavigationUtils.addNewScreen(
                    navigator = it,
                    screen = CheckCodeRoute()
                )
            }
        }
    }

    private fun onLoadUserInformation() {
        viewModelScope.launch {
            val result = getUserInformationUseCase()

            result.fold(
                onSuccess = { userInformation ->
                    viewModelState.update {
                        it.copy(
                            userName = userInformation.name,
                            photoUrl = userInformation.photoUrl
                        )
                    }
                },
                onFailure = { throwable ->
                    throwable.handleHttpException(
                        onUnauthorized = {
                            logoutUseCase(navigator = navigator)
                        }
                    )
                }
            )
        }
    }

    private fun onLoadAvailableCarRide() {
        viewModelScope.launch {
            onUpdateLoadingState(true)
            onUpdateRefresh(true)

            val result = availableCarRidesUseCase()

            result.fold(
                onSuccess = { availableCarRides ->
                    onUpdateLoadingState(false)
                    onUpdateRefresh(false)
                    onUpdateErrorState(false)
                    onUpdateAvailableCarRides(availableCarRides)
                },
                onFailure = { throwable ->
                    throwable.handleHttpException(
                        onUnauthorized = {
                            navigator.parent?.let { logoutUseCase(navigator = it) }
                        },
                        others = {
                            onUpdateLoadingState(false)
                            onUpdateRefresh(false)
                            onUpdateErrorState(true)
                        }
                    )
                }
            )
        }
    }

    private fun onNavigateToRideDetails(id: String) {
        // Pagina de details
        navigator.parent?.let {
            NavigationUtils.addNewScreen(
                navigator = it,
                screen = CarRideDetailsRoute(id)
            )
        }
    }

    private fun onUpdateLoadingState(isLoading: Boolean) {
        viewModelState.update { it.copy(isLoading = isLoading) }
    }

    private fun onUpdateRefresh(isRefresh: Boolean) {
        viewModelState.update { it.copy(isRefresh = isRefresh) }
    }

    private fun onUpdateErrorState(isError: Boolean) {
        viewModelState.update { it.copy(isError = isError) }
    }

    private fun onUpdateAvailableCarRides(availableCarRides: List<AvailableCarRide>) {
        viewModelState.update { it.copy(availableCarRideList = availableCarRides) }
    }
}