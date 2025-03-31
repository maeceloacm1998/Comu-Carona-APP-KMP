package org.app.marcelodev.comucarona.feature.carridedetails.ui

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.navigator.Navigator
import org.app.marcelodev.comucarona.feature.carridedetails.data.models.CarRideDetails
import org.app.marcelodev.comucarona.feature.carridedetails.domain.GetCarRideDetailsUseCase
import org.app.marcelodev.comucarona.feature.carridedetails.domain.ReservationRideUseCase
import org.app.marcelodev.comucarona.feature.carridedetails.ui.CarRideDetailsViewModelEventState.OnBack
import org.app.marcelodev.comucarona.feature.carridedetails.ui.CarRideDetailsViewModelEventState.OnCallPhone
import org.app.marcelodev.comucarona.feature.carridedetails.ui.CarRideDetailsViewModelEventState.OnCallWhatsApp
import org.app.marcelodev.comucarona.feature.carridedetails.ui.CarRideDetailsViewModelEventState.OnDismissBottomSheet
import org.app.marcelodev.comucarona.feature.carridedetails.ui.CarRideDetailsViewModelEventState.OnFetchReservationRide
import org.app.marcelodev.comucarona.feature.carridedetails.ui.CarRideDetailsViewModelEventState.OnGoToHome
import org.app.marcelodev.comucarona.feature.carridedetails.ui.CarRideDetailsViewModelEventState.OnOpenBottomSheet
import org.app.marcelodev.comucarona.feature.carridedetails.ui.CarRideDetailsViewModelEventState.OnOpenShare
import org.app.marcelodev.comucarona.feature.carridedetails.ui.CarRideDetailsViewModelEventState.OnReservationRide
import org.app.marcelodev.comucarona.feature.carridedetails.ui.CarRideDetailsViewModelEventState.OnRetry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.app.marcelodev.comucarona.commons.usecase.CallPhoneUseCase
import org.app.marcelodev.comucarona.commons.usecase.CallWhatsappUseCase
import org.app.marcelodev.comucarona.commons.usecase.CallWhatsappUseCase.Companion.DEFAULT_MESSAGE_CAR_RIDE
import org.app.marcelodev.comucarona.commons.usecase.LogoutUseCase
import org.app.marcelodev.comucarona.commons.usecase.ShareLinkUseCase
import org.app.marcelodev.comucarona.commons.utils.NavigationUtils
import org.app.marcelodev.comucarona.components.snackbar.SnackbarCustomType
import org.app.marcelodev.comucarona.components.snackbar.SnackbarCustomType.ERROR
import org.app.marcelodev.comucarona.components.snackbar.SnackbarCustomType.WARNING
import org.app.marcelodev.comucarona.feature.home.HomeRoutePatern
import org.app.marcelodev.comucarona.service.ktor.extensions.handleHttpException

class CarRideDetailsViewModel(
    private val navigator: Navigator,
    private val snackbarHostState: SnackbarHostState,
    private val riderId: String,
    private val getCarRideDetails: GetCarRideDetailsUseCase,
    private val reservationRideUseCase: ReservationRideUseCase,
    private val callWhatsappUseCase: CallWhatsappUseCase,
    private val callPhoneUseCase: CallPhoneUseCase,
    private val shareLinkUseCase: ShareLinkUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {
    private val fullSeatsMessage = "Todas as vagas dessa carona ja foram preechidas! \uD83D\uDE25"
    private val existingReservationMessage = "Você já está cadastrado nessa carona! 😁✌️"
    private val viewModelState = MutableStateFlow(CarRideDetailsViewModelState())

    val uiState = viewModelState
        .map(CarRideDetailsViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    fun onEvent(event: CarRideDetailsViewModelEventState) {
        when (event) {
            is OnFetchReservationRide -> onFetchReservationRide()
            is OnReservationRide -> onFetchReservationRide()
            is OnDismissBottomSheet -> onDismissBottomSheet()
            is OnOpenBottomSheet -> onOpenBottomSheet()
            is OnCallWhatsApp -> onCallWhatsApp()
            is OnCallPhone -> onCallPhone()
            is OnGoToHome -> onGoToHome()
            is OnRetry -> onFetchCarRideDetails(riderId)
            is OnOpenShare -> onOpenShareLink()
            is OnBack -> NavigationUtils.removeLastScreen(navigator)
        }
    }

    fun clearState() {
        viewModelState.update { CarRideDetailsViewModelState() }
        snackbarHostState.currentSnackbarData?.dismiss()
    }

    fun onFetchCarRideDetails(id: String) {
        onUpdateError(false)
        onUpdateLoading(true)

        viewModelScope.launch {
            getCarRideDetails.invoke(id)
                .onSuccess { result ->
                    onUpdateLoading(false)
                    onUpdateCarRideDetails(result)

                    if (result.isFullSeats) {
                        onUpdateIsEnableButton(false)
                        onUpdateShowSnackBar(
                            showSnackBar = true,
                            snackBarMessage = fullSeatsMessage,
                            snackbarType = WARNING
                        )
                    }

                    if(result.existingReservation) {
                        onUpdateIsEnableButton(false)
                        onUpdateShowSnackBar(
                            showSnackBar = true,
                            snackBarMessage = existingReservationMessage,
                            snackbarType = WARNING
                        )
                    }
                }
                .onFailure { throwable ->
                    throwable.handleHttpException(
                        onUnauthorized = {
                            logoutUseCase(navigator)
                        },
                        others = {
                            onUpdateLoading(false)
                            onUpdateError(true)
                        }
                    )
                }
        }
    }

    private fun onFetchReservationRide() {
        onUpdateLoadingReservation(true)

        viewModelScope.launch {
            reservationRideUseCase.invoke(riderId)
                .onSuccess {
                    onUpdateLoadingReservation(false)
                    onUpdateSuccessReservation(true)
                }
                .onFailure { throwable ->
                    throwable.handleHttpException(
                        onUnauthorized = {
                            logoutUseCase(navigator)
                        },
                        others = {
                            onUpdateLoadingReservation(false)
                            onUpdateShowSnackBar(
                                showSnackBar = true,
                                snackBarMessage = throwable.message ?: "",
                                snackbarType = ERROR
                            )
                        }
                    )
                }
        }
    }

    private fun onUpdateCarRideDetails(carRideDetails: CarRideDetails) {
        viewModelState.update { it.copy(carRideDetailsResponse = carRideDetails) }
    }

    private fun onUpdateShowSnackBar(
        showSnackBar: Boolean,
        snackBarMessage: String,
        snackbarType: SnackbarCustomType
    ) {
        viewModelState.update {
            it.copy(
                showSnackBar = showSnackBar,
                snackbarType = snackbarType
            )
        }

        viewModelScope.launch {
            snackbarHostState.showSnackbar(snackBarMessage)
        }
    }

    private fun onDismissBottomSheet() {
        viewModelState.update { it.copy(showBottomSheet = false) }
    }

    private fun onOpenBottomSheet() {
        viewModelState.update { it.copy(showBottomSheet = true) }
    }

    private fun onCallPhone() {
        val data = checkNotNull(viewModelState.value.carRideDetailsResponse)
        callPhoneUseCase(
            phoneNumber = data.bottomSheetCarRideUser.bottomSheetRiderPhoneNumber,
            onErrorAction = { errorMessage ->
                onUpdateShowSnackBar(
                    showSnackBar = true,
                    snackBarMessage = errorMessage,
                    snackbarType = ERROR
                )
            }
        )
    }

    private fun onCallWhatsApp() {
        val data = checkNotNull(viewModelState.value.carRideDetailsResponse)
        callWhatsappUseCase(
            phoneNumber = data.bottomSheetCarRideUser.bottomSheetRiderPhoneNumber,
            message = DEFAULT_MESSAGE_CAR_RIDE
        )
    }

    private fun onGoToHome() {
        NavigationUtils.replaceAllScreens(
            navigator = navigator,
            screen = HomeRoutePatern()
        )
    }

    private fun onOpenShareLink() {
        val data = checkNotNull(viewModelState.value.carRideDetailsResponse)
        shareLinkUseCase(
            link = data.shareDeeplink,
            onErrorAction = {
                onUpdateShowSnackBar(
                    showSnackBar = true,
                    snackBarMessage = it,
                    snackbarType = ERROR
                )
            })
    }

    private fun onUpdateSuccessReservation(isSuccess: Boolean) {
        viewModelState.update { it.copy(isSuccessReservation = isSuccess) }
    }

    private fun onUpdateLoadingReservation(isLoading: Boolean) {
        viewModelState.update { it.copy(isLoadingReservation = isLoading) }
    }

    private fun onUpdateLoading(isLoading: Boolean) {
        viewModelState.update { it.copy(isLoading = isLoading) }
    }

    private fun onUpdateError(error: Boolean) {
        viewModelState.update { it.copy(isError = error) }
    }

    private fun onUpdateIsEnableButton(isEnableButton: Boolean) {
        viewModelState.update { it.copy(isEnableButton = isEnableButton) }
    }

}